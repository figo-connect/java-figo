/*
 * The MIT License
 *
 * Copyright 2015 figo GmbH.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package me.figo;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

import me.figo.internal.FigoTrustManager;
import me.figo.internal.GsonAdapter;

/**
 *
 * @author halber
 */
public class FigoApi {
    
    private final String apiEndpoint;
    private final String authorization;
    private int timeout;
    
    public FigoApi(String apiEndpoint, String authorization, int timeout) {
        this.apiEndpoint = apiEndpoint;
        this.authorization = authorization;
        this.timeout = timeout;
    }
    
    /**
     * Helper method for making a OAuth2-compliant API call
     * 
     * @param path
     *            path on the server to call
     * @param data
     *            Payload of the request
     * @param method
     *            the HTTP verb to use
     * @param typeOfT
     *            Type of expected response
     * @param <T>
     *            Type of expected response
     * @return the parsed result of the request
     * 
     * @exception FigoException Base class for all figoExceptions
     * @exception IOException IOException
     */
    public <T> T queryApi(String path, Object data, String method, Type typeOfT) throws IOException, FigoException {
        URL url = new URL(apiEndpoint + path);

        // configure URL connection, i.e. the HTTP request
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setConnectTimeout(timeout);
        connection.setReadTimeout(timeout);
        
        setupTrustManager(connection);

        connection.setRequestMethod(method);
        connection.setRequestProperty("Authorization", authorization);
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestProperty("Content-Type", "application/json");

        // add payload
        if (data != null) {
            String encodedData = createGson().toJson(data);

            connection.setDoOutput(true);
            connection.getOutputStream().write(encodedData.getBytes(Charset.forName("UTF-8")));
        }

        return processResponse(connection, typeOfT);
    }

    /**
     * Method to configure TrustManager.
     * @param connection
     * 
     * @exception IOException IOException
     */
    protected void setupTrustManager(HttpURLConnection connection) throws IOException {
        if (connection instanceof HttpsURLConnection) {
            // Setup and install the trust manager
            try {
                final SSLContext sc = SSLContext.getInstance("SSL");
                sc.init(null, new TrustManager[] { new FigoTrustManager() }, new java.security.SecureRandom());
                ((HttpsURLConnection) connection).setSSLSocketFactory(sc.getSocketFactory());
            } catch (NoSuchAlgorithmException e) {
                throw new IOException("Connection setup failed", e);
            } catch (KeyManagementException e) {
                throw new IOException("Connection setup failed", e);
            }
        }
    }

    /**
     * Method to process the response.
     * @param <T>
     * @param connection
     * @param typeOfT
     * @return
     * 
     * @exception FigoException Base class for all figoExceptions
     * @exception IOException IOException
     */
    protected <T> T processResponse(HttpURLConnection connection, Type typeOfT) throws IOException, FigoException {
        // process response
        int code = connection.getResponseCode();
        if (code >= 200 && code < 300) {
            return handleResponse(connection.getInputStream(), typeOfT);
        } else if (code == 400) {
            throw new FigoException((FigoException.ErrorResponse) handleResponse(connection.getErrorStream(), FigoException.ErrorResponse.class));
        } else if (code == 401) {
            throw new FigoException("access_denied", "Access Denied");
        } else {
            // return decode(connection.getErrorStream(), resultType);
            throw new FigoException("internal_server_error", "We are very sorry, but something went wrong");
        }
    }
    
    /**
     * Handle the response of a request by decoding its JSON payload
     * 
     * @param stream
     *            Stream containing the JSON data
     * @param typeOfT
     *            Type of the data to be expected
     * @return Decoded data
     */
    protected <T> T handleResponse(InputStream stream, Type typeOfT) {
        // check whether decoding is actual requested
        if (typeOfT == null)
            return null;

        // read stream body
        Scanner s = new Scanner(stream, "UTF-8");
        s.useDelimiter("\\A");
        String body = s.hasNext() ? s.next() : "";
        s.close();

        // decode JSON payload
        return createGson().fromJson(body, typeOfT);
    }
    
    /**
     * Instantiate the GSON class. Meant to be overridden in order to provide custom Gson settings.
     * 
     * @return GSON instance
     */
    protected Gson createGson() {
        return GsonAdapter.createGson();
    }

    public String getApiEndpoint() {
        return apiEndpoint;
    }

    /**
     * The timeout used for queries.
     * @return 
     */
    public int getTimeout() {
        return timeout;
    }

    /**
     * @param timeout the timeout used for queries
     */
    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }
}
