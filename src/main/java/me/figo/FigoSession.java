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
import java.net.URLEncoder;
import java.util.List;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

import me.figo.FigoException.ErrorResponse;
import me.figo.internal.FigoTrustManager;
import me.figo.internal.GsonAdapter;
import me.figo.internal.SubmitPaymentRequest;
import me.figo.internal.SyncTokenRequest;
import me.figo.internal.TaskTokenResponse;
import me.figo.models.Account;
import me.figo.models.AccountBalance;
import me.figo.models.Bank;
import me.figo.models.Notification;
import me.figo.models.Payment;
import me.figo.models.Transaction;
import me.figo.models.User;

import com.google.gson.Gson;

/**
 * Main entry point to the data access-part of the figo connect java library. Here you can retrieve all the data the user granted your app access to.
 * 
 * @author Stefan Richter <stefan.richter@figo.me>
 */
public class FigoSession {

    protected String apiEndpoint;

    protected String access_token;

    protected int    timeout;

    /**
     * Creates a FigoSession instance
     * 
     * @param access_token
     *            the access token to bind this session to a user
     */
    public FigoSession(String accessToken) {
        this(accessToken, 5000);
    }

    /**
     * Creates a FigoSession instance
     * 
     * @param access_token
     *            the access token to bind this session to a user
     * @param timeout
     *            the timeout used for queries
     */
    public FigoSession(String accessToken, int timeout) {
        this(accessToken, timeout, "https://api.figo.me");
    }

    /**
     * Creates a FigoSession instance
     * 
     * @param accessToken
     *            the access token to bind this session to a user
     * @param timeout
     *            the timeout used for queries
     * @param apiEndpoint
     *            which endpoint to use (customize for different figo deployment)
     */
    public FigoSession(String accessToken, int timeout, String apiEndpoint) {
        this.access_token = accessToken;
        this.timeout = timeout;
        this.apiEndpoint = apiEndpoint;
    }

    /**
     * Query the figo API via HTTP
     * 
     * @param path
     *            Endpoint to query
     * @param data
     *            Payload to send
     * @param method
     *            HTTP verb to use
     * @return decoded response
     */
    protected <T> T queryApi(String path, Object data, String method, Type typeOfT) throws FigoException, IOException {
        // configure URL connection, i.e. the HTTP request
        HttpURLConnection connection = (HttpURLConnection) (new URL(apiEndpoint + path)).openConnection();
        connection.setConnectTimeout(timeout);
        connection.setReadTimeout(timeout);

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
        if (data != null) {
            String encodedData = createGson().toJson(data);

            connection.setDoOutput(true);
            connection.getOutputStream().write(encodedData.getBytes("UTF-8"));
        }

        // process response
        int code = connection.getResponseCode();
        if (code >= 200 && code < 300) {
            if (typeOfT == null)
                return null;
            else
                return handleResponse(connection.getInputStream(), typeOfT);
        } else if (code == 400) {
            throw new FigoException((ErrorResponse) handleResponse(connection.getErrorStream(), FigoException.ErrorResponse.class));
        } else if (code == 401) {
            throw new FigoException("access_denied", "Access Denied");
        } else if (code == 404) {
            return null;
        } else {
            // return decode(connection.getErrorStream(), resultType);
            throw new FigoException("internal_server_error", "We are very sorry, but something went wrong");
        }
    }

    /**
     * Instantiate the GSON class. Meant to be overridden in order to provide custom Gson settings.
     * 
     * @return GSON instance
     */
    protected Gson createGson() {
        return GsonAdapter.createGson();
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
        return createGson().fromJson(body, typeOfT);
    }

    /**
     * Get the current figo Account
     * 
     * @return User for the current figo Account
     */
    public User getUser() throws FigoException, IOException {
        return this.queryApi("/rest/user", null, "GET", User.class);
    }

    /**
     * Modify figo Account
     * 
     * @param user
     *            modified user object to be saved
     * @return User object for the updated figo Account
     */
    public User updateUser(User user) throws FigoException, IOException {
        return this.queryApi("/rest/user", user, "PUT", User.class);
    }

    /**
     * Delete figo Account
     */
    public void removeUser() throws FigoException, IOException {
        this.queryApi("/rest/user", null, "DELETE", null);
    }

    /**
     * All accounts the user has granted your App access to
     * 
     * @return List of Accounts
     */
    public List<Account> getAccounts() throws FigoException, IOException {
        Account.AccountsResponse response = this.queryApi("/rest/accounts", null, "GET", Account.AccountsResponse.class);
        return response == null ? null : response.getAccounts();
    }

    /**
     * Returns the account with the specified ID
     * 
     * @param accountId
     *            figo ID of the account to be retrieved
     * @return Account or Null
     */
    public Account getAccount(String accountId) throws FigoException, IOException {
        return this.queryApi("/rest/accounts/" + accountId, null, "GET", Account.class);
    }

    /**
     * Modify an account
     * 
     * @param account
     *            the modified account to be saved
     * @return Account object for the updated account returned by server
     */
    public Account updateAccount(Account account) throws FigoException, IOException {
        return this.queryApi("/rest/accounts/" + account.getAccountId(), account, "PUT", Account.class);
    }

    /**
     * Remove an account
     * 
     * @param accountId
     *            ID of the account to be removed
     */
    public void removeAccount(String accountId) throws FigoException, IOException {
        this.queryApi("/rest/accounts/" + accountId, null, "DELETE", null);
    }

    /**
     * Remove an account
     * 
     * @param account
     *            Account to be removed
     */
    public void removeAccount(Account account) throws FigoException, IOException {
        removeAccount(account.getAccountId());
    }

    /**
     * Returns the balance details of the account with he specified ID
     * 
     * @param accountId
     *            figo ID of the account to be retrieved
     * @return AccountBalance or Null
     */
    public AccountBalance getAccountBalance(String accountId) throws FigoException, IOException {
        return this.queryApi("/rest/accounts/" + accountId + "/balance", null, "GET", AccountBalance.class);
    }

    /**
     * Returns the balance details of the supplied account
     * 
     * @param account
     *            account whos balance should be retrieved
     * @return AccountBalance or Null
     */
    public AccountBalance getAccountBalance(Account account) throws FigoException, IOException {
        return getAccountBalance(account.getAccountId());
    }

    /**
     * Modify balance or account limits
     * 
     * @param accountId
     *            ID of the account to be modified
     * @param accountBalance
     *            modified AccountBalance object to be saved
     * @return AccountBalance object for the updated account as returned by the server
     */
    public AccountBalance updateAccountBalance(String accountId, AccountBalance accountBalance) throws FigoException, IOException {
        return this.queryApi("/rest/accounts/" + accountId + "/balance", accountBalance, "PUT", AccountBalance.class);
    }

    /**
     * Modify balance or account limits
     * 
     * @param account
     *            account to be modified
     * @param accountBalance
     *            modified AccountBalance object to be saved
     * @return AccountBalance object for the updated account as returned by the server
     */
    public AccountBalance updateAccountBalance(Account account, AccountBalance accountBalance) throws FigoException, IOException {
        return updateAccountBalance(account.getAccountId(), accountBalance);
    }

    /**
     * All transactions on all account of the user
     * 
     * @return List of Transaction objects
     */
    public List<Transaction> getTransactions() throws FigoException, IOException {
        return getTransactions((String) null);
    }

    /**
     * Retrieve all transactions on a specific account of the user
     * 
     * @param accountId
     *            the ID of the account for which to retrieve the transactions
     * @return List of Transactions
     */
    public List<Transaction> getTransactions(String accountId) throws FigoException, IOException {
        return this.getTransactions(accountId, null, null, null, null);
    }

    /**
     * Retrieve all transactions on a specific account of the user
     * 
     * @param account
     *            the account for which to retrieve the transactions
     * @return List of Transactions
     */
    public List<Transaction> getTransactions(Account account) throws FigoException, IOException {
        return this.getTransactions(account, null, null, null, null);
    }

    /**
     * Get an array of Transaction objects, one for each transaction of the user matching the criteria. Provide null values to not use the option.
     * 
     * @param account
     *            account for which to list the transactions
     * @param since
     *            this parameter can either be a transaction ID or a date
     * @param count
     *            limit the number of returned transactions
     * @param offset
     *            which offset into the result set should be used to determin the first transaction to return (useful in combination with count)
     * @param include_pending
     *            this flag indicates whether pending transactions should be included in the response; pending transactions are always included as a complete
     *            set, regardless of the `since` parameter
     * @return an array of Transaction objects
     */
    public List<Transaction> getTransactions(Account account, String since, Integer count, Integer offset, Boolean include_pending) throws FigoException,
            IOException {
        return getTransactions(account == null ? null : account.getAccountId(), since, count, offset, include_pending);
    }

    /**
     * Get an array of Transaction objects, one for each transaction of the user matching the criteria. Provide null values to not use the option.
     * 
     * @param accountId
     *            ID of the account for which to list the transactions
     * @param since
     *            this parameter can either be a transaction ID or a date
     * @param count
     *            limit the number of returned transactions
     * @param offset
     *            which offset into the result set should be used to determin the first transaction to return (useful in combination with count)
     * @param include_pending
     *            this flag indicates whether pending transactions should be included in the response; pending transactions are always included as a complete
     *            set, regardless of the `since` parameter
     * @return an array of Transaction objects
     */
    public List<Transaction> getTransactions(String accountId, String since, Integer count, Integer offset, Boolean include_pending) throws FigoException,
            IOException {
        StringBuilder sb = new StringBuilder();
        if (accountId == null) {
            sb.append("/rest/transactions?");
        } else {
            sb.append("/rest/accounts/" + accountId + "/transactions?");
        }
        if (since != null) {
            sb.append("since=");
            sb.append(URLEncoder.encode(since, "ISO-8859-1"));
            sb.append("&");
        }
        if (count != null) {
            sb.append("count=");
            sb.append(count);
            sb.append("&");
        }
        if (offset != null) {
            sb.append("offset=");
            sb.append(offset);
            sb.append("&");
        }
        if (include_pending != null) {
            sb.append("include_pending=");
            sb.append(include_pending ? "1" : "0");
        }
        Transaction.TransactionsResponse response = this.queryApi(sb.toString(), null, "GET", Transaction.TransactionsResponse.class);
        return response == null ? null : response.getTransactions();
    }

    /**
     * Retrieve a specific transaction by ID
     * 
     * @param accountId
     *            ID of the account on which the transaction occured
     * @param transactionId
     *            the figo ID of the specific transaction
     * @return Transaction or null
     */
    public Transaction getTransaction(String accountId, String transactionId) throws FigoException, IOException {
        return this.queryApi("/rest/accounts/" + accountId + "/transactions/" + transactionId, null, "GET", Transaction.class);
    }

    /**
     * Get bank
     * 
     * @param bankId
     *            ID of the bank to be retrieved
     * @return Bank or null
     */
    public Bank getBank(String bankId) throws FigoException, IOException {
        return this.queryApi("/rest/banks/" + bankId, null, "GET", Bank.class);
    }

    /**
     * Get Bank for account
     * 
     * @param account
     *            Account for which to return the Bank
     * @return Bank or Null
     */
    public Bank getBank(Account account) throws FigoException, IOException {
        return getBank(account.getBankId());
    }

    /**
     * Modify a bank
     * 
     * @param bank
     *            modified bank object to be saved
     * @return Bank object for the updated bank
     */
    public Bank updateBank(Bank bank) throws FigoException, IOException {
        return this.queryApi("/rest/banks/" + bank.getBankId(), bank, "PUT", Bank.class);
    }

    /**
     * Remove the stored PIN for a bank (if there was one)
     * 
     * @param bankId
     *            ID of the bank whose pin should be removed
     */
    public void removeBankPin(String bankId) throws FigoException, IOException {
        this.queryApi("/rest/banks/" + bankId + "/remove_pin", null, "POST", null);
    }

    /**
     * Remove the stored PIN for a bank (if there was one)
     * 
     * @param bank
     *            bank whose pin should be removed
     */
    public void removeBankPin(Bank bank) throws FigoException, IOException {
        removeBankPin(bank.getBankId());
    }

    /**
     * All notifications registered by this client for the user
     * 
     * @return List of Notification objects
     */
    public List<Notification> getNotifications() throws FigoException, IOException {
        Notification.NotificationsResponse response = this.queryApi("/rest/notifications", null, "GET", Notification.NotificationsResponse.class);
        return response == null ? null : response.getNotifications();
    }

    /**
     * Retrieve a specific notification by ID
     * 
     * @param notificationId
     *            figo ID for the notification to be retrieved
     * @return Notification or Null
     */
    public Notification getNotification(String notificationId) throws FigoException, IOException {
        return this.queryApi("/rest/notifications/" + notificationId, null, "GET", Notification.class);
    }

    /**
     * Register a new notification on the server for the user
     * 
     * @param notification
     *            Notification which should be registered
     * @return the newly registered Notification
     */
    public Notification addNotification(Notification notification) throws FigoException, IOException {
        return this.queryApi("/rest/notifications", notification, "POST", Notification.class);
    }

    /**
     * Update a stored notification
     * 
     * @param notification
     *            Notification with updated values
     */
    public Notification updateNotification(Notification notification) throws FigoException, IOException {
        return this.queryApi("/rest/notifications/" + notification.getNotificationId(), notification, "PUT", Notification.class);
    }

    /**
     * Remove a stored notification from the server
     * 
     * @param notification
     *            Notification to be removed
     */
    public void removeNotification(Notification notification) throws FigoException, IOException {
        this.queryApi("/rest/notifications/" + notification.getNotificationId(), null, "DELETE", null);
    }

    /**
     * Retrieve all payments
     * 
     * @return List of Payments
     */
    public List<Payment> getPayments() throws FigoException, IOException {
        Payment.PaymentsResponse response = this.queryApi("/rest/payments", null, "GET", Payment.PaymentsResponse.class);
        return response == null ? null : response.getPayments();
    }

    /**
     * Retrieve all payments on a certain account
     * 
     * @param accountId
     *            the ID of the account for which to retrieve the payments
     * @return List of Payments
     */
    public List<Payment> getPayments(String accountId) throws FigoException, IOException {
        Payment.PaymentsResponse response = this.queryApi("/rest/accounts/" + accountId + "/payments", null, "GET", Payment.PaymentsResponse.class);
        return response == null ? null : response.getPayments();
    }

    /**
     * all payments on a certain account
     * 
     * @param account
     *            the account for which to retrieve the payments
     * @return List of Payments
     */
    public List<Payment> getPayments(Account account) throws FigoException, IOException {
        return this.getPayments(account.getAccountId());
    }

    /**
     * Retrieve a specific payment by ID
     * 
     * @param accountId
     *            ID of the account on which the payment can be found
     * @param paymentID
     *            ID of the payment to be retrieved
     * @return Payment or Null
     */
    public Payment getPayment(String accountId, String paymentId) throws FigoException, IOException {
        return this.queryApi("/rest/accounts/" + accountId + "/payments/" + paymentId, null, "GET", Payment.class);
    }

    /**
     * Retrieve a specific payment by ID
     * 
     * @param account
     *            the account on which the payment can be found
     * @param paymentId
     *            ID of the payment to be retrieved
     * @return Payment or Null
     */
    public Payment getPayment(Account account, String paymentId) throws FigoException, IOException {
        return this.getPayment(account.getAccountId(), paymentId);
    }

    /**
     * Create a new payment
     * 
     * @param payment
     *            Payment which should be created
     * @return the newly created payment
     */
    public Payment addPayment(Payment payment) throws FigoException, IOException {
        return this.queryApi("/rest/accounts/" + payment.getAccountId() + "/payments", payment, "POST", Payment.class);
    }

    /**
     * Update a stored payment
     * 
     * @param payment
     *            Payment with updated values
     * @return updated Payment as returned by the server
     */
    public Payment updatePayment(Payment payment) throws FigoException, IOException {
        return this.queryApi("/rest/accounts/" + payment.getAccountId() + "/payments/" + payment.getPaymentId(), payment, "PUT", Payment.class);
    }

    /**
     * Remove a stored payment from the server
     * 
     * @param payment
     *            payment to be removed
     */
    public void removePayment(Payment payment) throws FigoException, IOException {
        this.queryApi("/rest/accounts/" + payment.getAccountId() + "/payments/" + payment.getPaymentId(), null, "DELETE", null);
    }

    /**
     * Submit payment to bank server
     * 
     * @param payment
     *            payment to be submitted
     * @param tanSchemeId
     *            TAN scheme ID of user-selected TAN scheme
     * @param state
     *            Any kind of string that will be forwarded in the callback response message
     * @return the URL to be opened by the user for the TAN process
     */
    public String submitPayment(Payment payment, String tanSchemeId, String state) throws FigoException, IOException {
        return submitPayment(payment, tanSchemeId, state, null);
    }

    /**
     * Submit payment to bank server
     * 
     * @param payment
     *            payment to be submitted
     * @param tanSchemeId
     *            TAN scheme ID of user-selected TAN scheme
     * @param state
     *            Any kind of string that will be forwarded in the callback response message
     * @param redirectUri
     *            At the end of the submission process a response will be sent to this callback URL
     * @return the URL to be opened by the user for the TAN process
     */
    public String submitPayment(Payment payment, String tanSchemeId, String state, String redirectUri) throws FigoException, IOException {
        TaskTokenResponse response = this.queryApi("/rest/accounts/" + payment.getAccountId() + "/payments/" + payment.getPaymentId() + "/submit",
                new SubmitPaymentRequest(tanSchemeId, state, redirectUri), "POST", TaskTokenResponse.class);
        return apiEndpoint + "/task/start?id=" + response.task_token;
    }

    /**
     * URL to trigger a synchronisation. The user should open this URL in a web browser to synchronize his/her accounts with the respective bank servers. When
     * the process is finished, the user is redirected to the provided URL.
     * 
     * @param state
     *            String passed on through the complete synchronization process and to the redirect target at the end. It should be used to validated the
     *            authenticity of the call to the redirect URL
     * @param redirect_url
     *            URI the user is redirected to after the process completes
     * @return the URL to be opened by the user
     */
    public String getSyncURL(String state, String redirect_url) throws FigoException, IOException {
        TaskTokenResponse response = this.queryApi("/rest/sync", new SyncTokenRequest(state, redirect_url), "POST", TaskTokenResponse.class);
        return apiEndpoint + "/task/start?id=" + response.task_token;
    }
}
