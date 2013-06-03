package me.figo;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

import org.apache.commons.codec.binary.Base64;

import com.google.gson.Gson;

import me.figo.FigoException.ErrorResponse;
import me.figo.internal.GsonAdapter;

/***
 * Representing a not user-bound connection to the figo connect API.
 *
 * Its main purpose is to let user login via the OAuth2 API.
 * 
 * @author Stefan Richter <stefan.richter@figo.me>
 *
 */
public class FigoConnection {

    private final String API_ENDPOINT = "https://api.leanbank.com";

    private String clientId = null;
    private String clientSecret = null;
    private String basicAuthInfo = null;
    private String redirectUri = null;
    
    /***
     * Creates a FigoConnection instance
     * 
     * @param clientId the OAuth Client ID as provided by your figo developer contact
     * @param clientSecret the OAuth Client Secret as provided by your figo developer contact
     * @param redirectUri the URI the users gets redirected to after the login is finished or if he presses cancels
     */
    public FigoConnection(String clientId, String clientSecret, String redirectUri) {
    	this.clientId = clientId;
    	this.clientSecret = clientSecret;
    	this.redirectUri = redirectUri;
    	
    	// compute basic auth information
		String authInfo = this.clientId + ":" + this.clientSecret;
		this.basicAuthInfo = Base64.encodeBase64String(authInfo.getBytes());
    }

    /***
      * Helper method for making a OAuth2-compliant API call
      * 
      * @param path path on the server to call
      * @param data Payload of the request
      * @param typeofT Type of expected response
      * @return
     * @throws IOException 
     * @throws FigoException 
      */
     public <T> T queryApi(String path, Object data, String method, Type typeOfT) throws IOException, FigoException {
 		URL url = new URL(API_ENDPOINT + path);
		
 		// configure URL connection, i.e. the HTTP request
 		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
 		connection.setRequestMethod(method);
		connection.setRequestProperty("Authorization", "Basic " + this.basicAuthInfo);
 		connection.setRequestProperty("Accept", "application/json");
 		connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
 		
 		// add payload
 		if(data != null) {
 			String encodedData = GsonAdapter.createGson().toJson(data);
 			
 			connection.setDoOutput(true);
 			connection.getOutputStream().write(encodedData.getBytes("UTF-8"));
 		}

 		// process response
 		int code = connection.getResponseCode();
 		if (code >= 200 && code < 300) {
 			return handleResponse(connection.getInputStream(), typeOfT);
 		} else if (code == 400) {
 			throw new FigoException((ErrorResponse) handleResponse(connection.getErrorStream(), FigoException.ErrorResponse.class));
 		} else if (code == 401) {
 			throw new FigoException("access_denied", "Access Denied");
 		} else {
 			//return decode(connection.getErrorStream(), resultType);
 			throw new FigoException("internal_server_error", "We are very sorry, but something went wrong");
 		}
     }

 	/***
 	 * Handle the response of a request by decoding its JSON payload
 	 * @param stream Stream containing the JSON data
 	 * @param typeOfT Type of the data to be expected
 	 * @return Decoded data
 	 */
 	private <T> T handleResponse(InputStream stream, Type typeOfT) {
 		// check whether decoding is actual requested
 		if (typeOfT == null)
 			return null;
 		
 		// read stream body
 		Scanner s = new Scanner(stream, "UTF-8");
 		s.useDelimiter("\\A");
 		String body = s.hasNext() ? s.next() : "";
 		s.close();
 		
 		// decode JSON payload
 		Gson gson = GsonAdapter.createGson();
 		return gson.fromJson(body, typeOfT);
 	}

     /***
      * The URL a user should open in his/her web browser to start the login process.
      * 
      * When the process is completed, the user is redirected to the URL provided to the constructor and passes on an authentication code. This code can be converted into an access token for data access.
      * 
      * @param scope Scope of data access to ask the user for, e.g. `accounts=ro`
      * @param state String passed on through the complete login process and to the redirect target at the end. It should be used to validated the authenticity of the call to the redirect URL
      *
      * @return the URL of the first page of the login process
      */
     public String getLoginUrl(String scope, String state) {
    	try {
			StringBuilder sb = new StringBuilder();
			sb.append(this.API_ENDPOINT);
			sb.append("/auth/code?response_type=code&client_id=");
			sb.append(URLEncoder.encode(this.clientId, "ISO-8859-1"));
			sb.append("&redirect_uri=");
			sb.append(URLEncoder.encode(this.redirectUri, "ISO-8859-1"));
			sb.append("&scope=");
			sb.append(URLEncoder.encode(scope, "ISO-8859-1"));
			sb.append("&state=");
			sb.append(URLEncoder.encode(state, "ISO-8859-1"));
			return sb.toString();
		} catch (UnsupportedEncodingException e) {
			return null;
		}
     }
     
     /***
      * Convert the authentication code received as result of the login process into an access token usable for data access.
      * 
      * @param authenticationCode the code received as part of the call to the redirect URL at the end of the logon process
      * @return HashMap with the following keys:
             - `access_token` - the access token for data access. You can pass it into `FigoConnection.open_session` to get a FigoSession and access the users data
             - `refresh_token` - if the scope contained the `offline` flag, also a refresh token is generated. It can be used to generate new access tokens, when the first one has expired.
             - `expires` - absolute time the access token expires
      */
     public String convertAuthenticationCode(String authenticationCode) throws FigoException, IOException {
         if (!authenticationCode.startsWith("O")) {
        	 throw new FigoException("invalid_code", "Invalid authentication code");
         }

         return this.queryApi("/auth/token", new TokenRequest(null, authenticationCode, this.redirectUri, "authorization_code"), "POST", TokenResponse.class); 
     }
        

     /***
      * Convert a refresh token (granted for offline access and returned by `convert_authentication_code`) into an access token usabel for data acccess.
      * 
      * @param refreshToken refresh token returned by `convert_authentication_code`
      * @return Dictionary with the following keys:
             - `access_token` - the access token for data access. You can pass it into `FigoConnection.open_session` to get a FigoSession and access the users data
             - `expires` - absolute time the access token expires
      */
     public String convertRefreshToken(String refreshToken) throws IOException, FigoException {
         if (!refreshToken.startsWith("R")) {
        	 throw new FigoException("invalid_code", "Invalid authentication code");
         }

         return this.queryApi("/auth/token", new TokenRequest(refreshToken, null, this.redirectUri, "refresh_token"), "POST", TokenResponse.class); 
     }


     /***
      * Revoke a granted access or refresh token and thereby invalidate it.
      * 
      * Note: this action has immediate effect, i.e. you will not be able use that token anymore after this call.
      * 
      * @param token access or refresh token to be revoked
      */
     public void revokeToken(String token) throws IOException, FigoException {
    	 try {
			this.queryApi("/auth/revoke?token=" + URLEncoder.encode(token, "ISO-8859-1"), null, "GET", null);
		} catch (UnsupportedEncodingException e) { }
     }
     
     /***
      * Helper type for OAuth token API
      */
     class TokenRequest {
    	 public String refresh_token = null;
    	 public String code = null;
    	 public String redirect_uri = null;
    	 public String grant_type = null;
    	 
    	 public TokenRequest(String refresh_token, String code, String redirect_uri, String grant_type) {
    		 this.refresh_token = refresh_token;
    		 this.code = code;
    		 this.redirect_uri = redirect_uri;
    		 this.grant_type = grant_type;
    	 }
     }
     
     /***
      * Helper type for OAuth token API
      */
     public class TokenResponse {
    	 public String access_token = null;
    	 public String refresh_token = null;
    	 public Integer expires = 0;
     }
}
