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
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

import me.figo.FigoException.ErrorResponse;
import me.figo.internal.FigoTrustManager;
import me.figo.internal.GsonAdapter;
import me.figo.models.Account;
import me.figo.models.AccountBalance;
import me.figo.models.Notification;
import me.figo.models.Transaction;

import com.google.gson.Gson;

/***
 * Main entry point to the data access-part of the figo connect java library. Here you can retrieve all the
 * data the user granted your app access to.
 * 
 * @author Stefan Richter <stefan.richter@figo.me>
 */
public class FigoSession {
	
	private final String API_ENDPOINT = "https://api.leanbank.com";
	
	private String access_token = null;

	/***
	 * Creates a FigoSession instance
	 * 
	 * @param access_token the access token to bind this session to a user
	 */
	public FigoSession(String access_token) {
		this.access_token = access_token;
	}
	
	/***
	 * Query the figo API via HTTP
	 * 
	 * @param path Endpoint to query
	 * @param data Payload to send
	 * @param method HTTP verb to use
	 * @return decoded response
	 */
	private <T> T queryApi(String path, Object data, String method, Type typeOfT) throws FigoException, IOException {
		// configure URL connection, i.e. the HTTP request
		HttpURLConnection connection = (HttpURLConnection) (new URL(API_ENDPOINT + path)).openConnection();
		if (connection instanceof HttpsURLConnection) {
	        // Setup and install the trust manager
	        try {
	        	final SSLContext sc = SSLContext.getInstance("SSL");
	        	sc.init(null, new TrustManager[] { new FigoTrustManager() }, new java.security.SecureRandom());
	        	((HttpsURLConnection) connection).setSSLSocketFactory(sc.getSocketFactory());
	        } catch (Exception e) {
	        	throw new IOException("Connection setup failed", e);
	        }
		}
		
		connection.setRequestMethod(method);
		connection.setRequestProperty("Authorization", "Bearer " + this.access_token);
		connection.setRequestProperty("Accept", "application/json");
		connection.setRequestProperty("Content-Type", "application/json");
		
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
		} else if (code == 404) {
			return null;
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
	 * All accounts the user has granted your App access to
	 * @return List of Accounts
	 */
	public List<Account> getAccounts() throws FigoException, IOException {
		Account.AccountsResponse response = this.queryApi("/rest/accounts", null, "GET", Account.AccountsResponse.class);
		if(response == null)
			return null;
		else
			return response.getAccounts();
	}
	
	/***
	 * Returns the account with the specified ID
	 * @param accountId figo ID for the account to be retrieved
	 * @return Account or Null
	 * @throws IOException 
	 * @throws FigoException 
	 */
	public Account getAccount(String accountId) throws FigoException, IOException {
		return this.queryApi("/rest/accounts/" + accountId, null, "GET", Account.class);
	}
	
	public AccountBalance getAccountBalance(String accountId) throws FigoException, IOException {
		return this.queryApi("/rest/accounts/" + accountId + "/balance", null, "GET", AccountBalance.class);
	}
	
	public AccountBalance getAccountBalance(Account account) throws FigoException, IOException {
		return getAccountBalance(account.getAccountId());
	}
	
	/***
	 * All transactions on all account of the user
	 * @return List of Transaction objects
	 */
	public List<Transaction> getTransactions() throws FigoException, IOException {
		Transaction.TransactionsResponse response = this.queryApi("/rest/transactions", null, "GET", Transaction.TransactionsResponse.class);
		if (response == null)
			return null;
		else
			return response.getTransactions();
	}

	/***
	 * All transactions of a specific account
	 * @return List of Transaction objects
	 */
	public List<Transaction> getTransactions(String accountId) throws FigoException, IOException {
		Transaction.TransactionsResponse response = this.queryApi("/rest/accounts/" + accountId +"/transactions", null, "GET", Transaction.TransactionsResponse.class);
		if (response == null)
			return null;
		else
			return response.getTransactions();
	}
	
	/***
	 * All transactions of a specific account
	 * @return List of Transaction objects
	 */
	public List<Transaction> getTransactions(Account account) throws FigoException, IOException {
		return getTransactions(account.getAccountId());
	}
	
	/***
	 * Retrieve a specific transaction by ID 
	 * @param transactionId the figo ID of the specific transaction
	 * @return Transaction or null
	 */
	public Transaction getTransaction(String transactionId) throws FigoException, IOException {
		return this.queryApi("/rest/transactions/" + transactionId, null, "GET", Transaction.class);
	}
	
	/***
	 * All notifications registered by this client for the user
	 * @return List of Notification objects
	 */
	public List<Notification> getNotifications() throws FigoException, IOException {
		Notification.NotificationsResponse response = this.queryApi("/rest/notifications", null, "GET", Notification.NotificationsResponse.class);
		if (response == null)
			return null;
		else
			return response.getNotifications();
	}
	
	/***
	 * Retrieve a specific notification by ID
	 * @param notificationId figo ID for the notification to be retrieved
	 * @return Notification or Null
	 */
	public Notification getNotification(String notificationId) throws FigoException, IOException {
		return this.queryApi("/rest/notifications/" + notificationId, null, "GET", Notification.class);
	}
	
	/***
	 * Register a new notification on the server for the user
	 * @param notification Notification which should be registered
	 * @return figo ID of the registered Notification
	 */
	public Notification addNotification(Notification notification) throws FigoException, IOException {
		return this.queryApi("/rest/notifications", notification, "POST", Notification.class);
	}

	/***
	 * Update a stored notification
	 * @param notification Notification with updated values
	 */
	public Notification updateNotification(Notification notification) throws FigoException, IOException {
		return this.queryApi("/rest/notifications/" + notification.getNotificationId(), notification, "PUT", Notification.class);
	}

	/***
	 * Remove a stored notification from the server
	 * @param notification Notification to be removed
	 */
	public void removeNotification(Notification notification) throws FigoException, IOException {
		this.queryApi("/rest/notifications/" + notification.getNotificationId(), null, "DELETE", null);
	}
	
	/***
	 * URL to trigger a synchronisation.
     * 
     *  The user should open this URL in a web browser to synchronize his/her accounts with the respective bank servers. When the process is finished, the user is redirected to the provided URL.
     *  
	 * @param state String passed on through the complete synchronization process and to the redirect target at the end. It should be used to validated the authenticity of the call to the redirect URL
	 * @param redirect_url URI the user is redirected to after the process completes
	 * @return the URL to be opened by the user
	 */
	public String getSyncURL(String state, String redirect_url) throws FigoException, IOException {
		@SuppressWarnings("unused")
		class SyncTokenRequest {
			public String state;
			public String redirect_uri;
			
			public SyncTokenRequest(String state, String redirect_uri) {
				this.state = state;
				this.redirect_uri = redirect_uri;
			}
		}
		class SyncTokenResponse {
			public String task_token;
		}
		
		SyncTokenResponse response = this.queryApi("/rest/sync", new SyncTokenRequest(state, redirect_url), "POST", SyncTokenResponse.class);
		
		return response.task_token;
	}
}
