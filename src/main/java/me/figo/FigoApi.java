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

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import com.google.gson.Gson;

import me.figo.internal.FigoSocketFactory;
import me.figo.internal.FigoTrustManager;
import me.figo.internal.GsonAdapter;
import me.figo.models.ErrorResponse;

/**
 *
 * 
 */
public class FigoApi {
    
    protected static final String API_FIGO_LIVE = "https://api.figo.me";
	protected static final String API_FIGO_STAGE = "https://staging.figo.me";
	private Logger logger = Logger.getLogger(this.getClass().getName());
	private final String apiEndpoint;
    private final String authorization;
    private int timeout;
    private X509TrustManager trustManager;
    private Proxy proxy;
    
    /**
     * 
     * @param apiEndpoint
     * @param authorization
     * @param timeout
     */
    public FigoApi(String apiEndpoint, String authorization, int timeout) {
        this.apiEndpoint = apiEndpoint;
        this.authorization = authorization;
        this.timeout = timeout;
        this.trustManager = new FigoTrustManager();
    }

    public FigoApi(String authorization, int timeout)   {
        this.authorization = authorization;
        this.timeout = timeout;
        this.trustManager = new FigoTrustManager();
        String endpointEnv = System.getenv("FIGO_API_ENDPOINT");
        if (endpointEnv != null)  {
            this.apiEndpoint = endpointEnv;
        }
        else    {
            this.apiEndpoint = API_FIGO_LIVE;
        }
    }
    
    public void setTrustManager(X509TrustManager trustManager)	{
    	this.trustManager = trustManager;
    }
    
    public void setProxy(Proxy proxy)	{
    	this.proxy = proxy;
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
        HttpURLConnection connection;

        // configure URL connection, i.e. the HTTP request
        if(this.proxy != null)	{
        	connection = (HttpURLConnection) url.openConnection(this.proxy);
        }
        else	{
        	connection = (HttpURLConnection) url.openConnection();
        }
        
        connection.setConnectTimeout(timeout);
        connection.setReadTimeout(timeout);
        
        setupTrustManager(connection, trustManager);

        connection.setRequestMethod(method);
        connection.setRequestProperty("Authorization", authorization);
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("User-Agent", "Figo/Java 3.0.0");

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
    protected void setupTrustManager(HttpURLConnection connection, X509TrustManager trustManager) throws IOException {
        if (connection instanceof HttpsURLConnection) {
            // Setup and install the trust manager
            try {
                final SSLContext sc = SSLContext.getInstance("SSL");
                sc.init(null, new TrustManager[] { trustManager }, new java.security.SecureRandom());
				FigoSocketFactory figoSocketFactory = new FigoSocketFactory(sc.getSocketFactory());
				((HttpsURLConnection) connection).setSSLSocketFactory(figoSocketFactory);
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
        } else {
            ErrorResponse errorResponse = handleResponse(connection.getErrorStream(), ErrorResponse.class);
			throw new FigoApiException(errorResponse);
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
