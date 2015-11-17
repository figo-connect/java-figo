//
// Copyright (c) 2013 figo GmbH
//
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
//
// The above copyright notice and this permission notice shall be included in
// all copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
// THE SOFTWARE.
//

package me.figo;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;

import me.figo.internal.CreateUserRequest;
import me.figo.internal.CreateUserResponse;
import me.figo.internal.CredentialLoginRequest;
import me.figo.internal.TokenRequest;
import me.figo.internal.TokenResponse;
import me.figo.models.BusinessProcess;
import me.figo.models.ProcessToken;

import org.apache.commons.codec.binary.Base64;

/**
 * Representing a not user-bound connection to the figo connect API. Its main purpose is to let user login via the OAuth2 API and/or create business processes.
 *
 * @author Stefan Richter
 */
public class FigoConnection extends FigoApi {

    protected String clientId;
    protected String clientSecret;
    protected String redirectUri;

    /**
     * Creates a FigoConnection instance
     *
     * @param clientId
     *            the OAuth Client ID as provided by your figo developer contact
     * @param clientSecret
     *            the OAuth Client Secret as provided by your figo developer contact
     * @param redirectUri
     *            the URI the users gets redirected to after the login is finished or if he presses cancels
     */
    public FigoConnection(String clientId, String clientSecret, String redirectUri) {
        this(clientId, clientSecret, redirectUri, 10000);
    }

    /**
     * Creates a FigoConnection instance
     *
     * @param clientId
     *            the OAuth Client ID as provided by your figo developer contact
     * @param clientSecret
     *            the OAuth Client Secret as provided by your figo developer contact
     * @param redirectUri
     *            the URI the users gets redirected to after the login is finished or if he presses cancels
     * @param timeout
     *            the timeout used for queries
     */
    public FigoConnection(String clientId, String clientSecret, String redirectUri, int timeout) {
        this(clientId, clientSecret, redirectUri, timeout, "https://api.figo.me");
    }

    /**
     * Creates a FigoConnection instance
     *
     * @param clientId
     *            the OAuth Client ID as provided by your figo developer contact
     * @param clientSecret
     *            the OAuth Client Secret as provided by your figo developer contact
     * @param redirectUri
     *            the URI the users gets redirected to after the login is finished or if he presses cancels
     * @param timeout
     *            the timeout used for queries
     * @param apiEndpoint
     *            which endpoint to use (customize for different figo deployment)
     */
    public FigoConnection(String clientId, String clientSecret, String redirectUri, int timeout, String apiEndpoint) {
        super(apiEndpoint, buildAuthorizationString(clientId, clientSecret), timeout);
        this.redirectUri = redirectUri;
    }

    private static String buildAuthorizationString(String clientId1, String clientSecret1) {
        String authInfo = clientId1 + ":" + clientSecret1;
        return "Basic " + Base64.encodeBase64String(authInfo.getBytes(Charset.forName("UTF-8")));
    }

    /**
     * The URL a user should open in his/her web browser to start the login process. When the process is completed, the user is redirected to the URL provided
     * to the constructor and passes on an authentication code. This code can be converted into an access token for data access.
     *
     * @param scope
     *            Scope of data access to ask the user for, e.g. `accounts=ro`
     * @param state
     *            String passed on through the complete login process and to the redirect target at the end. It should be used to validated the authenticity of
     *            the call to the redirect URL
     * @return the URL of the first page of the login process
     */
    public String getLoginUrl(String scope, String state) {
        try {
            return getApiEndpoint()
                    + "/auth/code?response_type=code&client_id=" + URLEncoder.encode(this.clientId, "ISO-8859-1")
                    + "&redirect_uri=" + URLEncoder.encode(this.redirectUri, "ISO-8859-1")
                    + "&scope=" + URLEncoder.encode(scope, "ISO-8859-1")
                    + "&state=" + URLEncoder.encode(state, "ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            // Every implementation of the Java platform has to support the ISO-8859-1 charsets.
            return null;
        }
    }

    /**
     * Convert the authentication code received as result of the login process into an access token usable for data access.
     *
     * @param authenticationCode
     *            the code received as part of the call to the redirect URL at the end of the long process
     * @return HashMap with the following keys: - `access_token` - the access token for data access. You can pass it into `FigoConnection.open_session` to get a
     *         FigoSession and access the users data - `refresh_token` - if the scope contained the `offline` flag, also a refresh token is generated. It can be
     *         used to generate new access tokens, when the first one has expired. - `expires_in` - absolute time the access token expires
     */
    public TokenResponse convertAuthenticationCode(String authenticationCode) throws FigoException, IOException {
        if (!authenticationCode.startsWith("O")) {
            throw new FigoException("invalid_code", "Invalid authentication code");
        }

        return this.queryApi("/auth/token", new TokenRequest(null, authenticationCode, this.redirectUri, "authorization_code"), "POST", TokenResponse.class);
    }

    /**
     * Convert a refresh token (granted for offline access and returned by `convert_authentication_code`) into an access token usable for data access.
     *
     * @param refreshToken
     *            refresh token returned by `convert_authentication_code`
     * @return Dictionary with the following keys: - `access_token` - the access token for data access. You can pass it into `FigoConnection.open_session` to
     *         get a FigoSession and access the users data - `expires_in` - absolute time the access token expires
     *         
     * @exception FigoException Base class for all figoExceptions
     * @exception IOException IOException        
     */
    public TokenResponse convertRefreshToken(String refreshToken) throws IOException, FigoException {
        if (!refreshToken.startsWith("R")) {
            throw new FigoException("invalid_code", "Invalid authentication code");
        }

        return this.queryApi("/auth/token", new TokenRequest(refreshToken, null, this.redirectUri, "refresh_token"), "POST", TokenResponse.class);
    }

    /**
     * Login an user with his figo username and password credentials
     * @param username
     * 			the user's figo username
     * @param password
     * 			the user's figo password
     * @return Dictionary with the following keys: - `access_token` - the access token for data access. You can pass it into `FigoConnection.open_session` to
     *         get a FigoSession and access the users data - `expires_in` - absolute time the access token expires
     *         
     * @exception FigoException Base class for all figoExceptions
     * @exception IOException IOException        
     */
    public TokenResponse credentialLogin(String username, String password) throws IOException, FigoException	{
    	return this.queryApi("/auth/token", new CredentialLoginRequest(username, password), "POST", TokenResponse.class);
    }

    /**
     * Revoke a granted access or refresh token and thereby invalidate it. Note: this action has immediate effect, i.e. you will not be able use that token
     * anymore after this call.
     *
     * @param token
     *            access or refresh token to be revoked
     *            
     * @exception FigoException Base class for all figoExceptions
     * @exception IOException IOException          
     */
    public void revokeToken(String token) throws IOException, FigoException {
        this.queryApi("/auth/revoke?token=" + URLEncoder.encode(token, "ISO-8859-1"), null, "GET", null);
    }

    /**
     * Create a new figo Account
     *
     * @param name
     *            First and last name
     * @param email
     *            Email address; It must obey the figo username and password policy
     * @param password
     *            New figo Account password; It must obey the figo username and password policy
     * @param language
     *            Two-letter code of preferred language
     *
     * @return Auto-generated recovery password
     * 
     * @exception FigoException Base class for all figoExceptions
     * @exception IOException IOException
     */
    public String addUser(String name, String email, String password, String language) throws IOException, FigoException {
        CreateUserResponse response = this.queryApi("/auth/user", new CreateUserRequest(name, email, password, language), "POST", CreateUserResponse.class);
        return response.recovery_password;
    }


    /**
     * Creates a new figo User and returns a login token
     * @param name
     *            First and last name
     * @param email
     *            Email address; It must obey the figo username and password policy
     * @param password
     *            New figo Account password; It must obey the figo username and password policy
     * @param language
     *            Two-letter code of preferred language
     * @return TokenResponse for further API requests
     * 
     * @exception FigoException Base class for all figoExceptions
     * @exception IOException IOException
     */
    public TokenResponse addUserAndLogin(String name, String email, String password, String language) throws IOException, FigoException {
        CreateUserResponse response = this.queryApi("/auth/user", new CreateUserRequest(name, email, password, language), "POST",
                CreateUserResponse.class);
        return this.credentialLogin(email, password);
    }
    
    /**
     * Start a new business process 
     * @param processToken 
     * 				The unique token that was created by createProcess
     * @throws FigoException
     * 				Base class for all figoExceptions
     * @throws IOException IOException
     */
    public void startProcess(ProcessToken processToken) throws FigoException, IOException	{
    	this.queryApi("/process/start?id=" + processToken.getProcessToken(), null, "GET", null);
    }
    
    /**
     * Create a new business process
     * @param process
     * 				BusinessProcess object which contains the figo user credentials and the processes to execute
     * @return Unique 
     * 				process token to use when starting the process
     * @throws FigoException 
     * 				Base class for all figoExceptions
     * @throws IOException IOException
     */
    public ProcessToken createProcess(BusinessProcess process) throws FigoException, IOException	{
    	return this.queryApi("/client/process", process, "POST", ProcessToken.class);
    }
}
