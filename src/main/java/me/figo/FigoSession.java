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
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.List;
import me.figo.internal.AccountOrderRequest;
import me.figo.internal.SetupAccountRequest;
import me.figo.internal.SubmitPaymentRequest;
import me.figo.internal.SyncTokenRequest;
import me.figo.internal.TaskStatusRequest;
import me.figo.internal.TaskStatusResponse;
import me.figo.internal.TaskTokenResponse;
import me.figo.internal.VisitedRequest;
import me.figo.models.Account;
import me.figo.models.AccountBalance;
import me.figo.models.Bank;
import me.figo.models.BusinessProcess;
import me.figo.models.LoginSettings;
import me.figo.models.PaymentContainer;
import me.figo.models.PaymentProposal;
import me.figo.models.PaymentProposal.PaymentProposalResponse;
import me.figo.models.ProcessToken;
import me.figo.models.Security;
import me.figo.models.Service;
import me.figo.models.Notification;
import me.figo.models.Payment;
import me.figo.models.Transaction;
import me.figo.models.User;

import java.util.Collections;

/**
 * Main entry point to the data access-part of the figo connect java library. 
 * Here you can retrieve all the data the user granted your app access to.
 * 
 * @author Stefan Richter
 */
public class FigoSession extends FigoApi {
    
    public enum PendingTransactions {
        INCLUDED,
        EXCLUDED
    }
    
    public enum FieldVisited {
        VISITED,
        NOT_VISITED
    }

    /**
     * Creates a FigoSession instance
     * 
     * @param accessToken
     *            the access token to bind this session to a user
     */
    public FigoSession(String accessToken) {
        this(accessToken, 5000);
    }

    /**
     * Creates a FigoSession instance
     * 
     * @param accessToken
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
        super(apiEndpoint, "Bearer " + accessToken, timeout);
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
     * Returns a list of all supported credit cards and payment services for a country
     * @param countryCode
     * @return List of Services
     */
    public List<Service> getSupportedServices(String countryCode) throws FigoException, IOException	{
    	Service.ServiceResponse response = this.queryApi("/rest/catalog/services/" + countryCode, null, "GET", Service.ServiceResponse.class);
    	return response == null ? null : response.getServices();
    }
    
    /**
     * Returns the login settings for a specified banking or payment service
     * @param countryCode
     * @param bankCode
     * @return LoginSettings
     */
    public LoginSettings getLoginSettings(String countryCode, String bankCode) throws FigoException, IOException	{
    	return this.queryApi("/rest/catalog/banks/" + countryCode + "/" + bankCode, null, "GET", LoginSettings.class);
    }    
    
    /**
     * Returns a TaskToken for a new account creation task
     * @param bankCode
     * @param countryCode
     * @param loginName
     * @param pin
     * @return
     */
    public TaskTokenResponse setupNewAccount(String bankCode, String countryCode, String loginName, String pin) throws FigoException, IOException	{
    	return this.queryApi("/rest/accounts", new SetupAccountRequest(bankCode, countryCode, loginName, pin), "POST", TaskTokenResponse.class);
    }
    
    /**
     * Setups an account an starts the initial syncronization directly
     * @param bankCode
     * @param countryCode
     * @param loginName
     * @param pin
     * @return TaskStatusResponse
     */
    public TaskStatusResponse setupAndSyncAccount(String bankCode, String countryCode, String loginName, String pin) throws FigoException, IOException, FigoPinException, InterruptedException	{
    	TaskTokenResponse tokenResponse = this.setupNewAccount(bankCode, countryCode, loginName, pin);
    	TaskStatusResponse taskStatus =  this.getTaskState(tokenResponse);
    	while("Connecting to server...".equals(taskStatus.getMessage()))	{
    		taskStatus = this.getTaskState(tokenResponse);
			Thread.sleep(1000);
    	}
    	if(taskStatus.isErroneous()
                && "Die Anmeldung zum Online-Zugang Ihrer Bank ist fehlgeschlagen. Bitte überprüfen Sie Ihre Zugangsdaten.".equals(taskStatus.getMessage())) {
    		throw new FigoPinException(bankCode, countryCode, loginName, pin);
    	}
    	else if(taskStatus.isErroneous()
                && "Ihr Online-Zugang wurde von Ihrer Bank gesperrt. Bitte lassen Sie die Sperre von Ihrer Bank aufheben.".equals(taskStatus.getMessage())){
    		throw new FigoException("", taskStatus.getMessage());
    	}
    	return taskStatus;
    }
    
    /**
     * Exception handler for a wrong pin. Starts a new task for account creation with a new pin
     * @param exception
     * 				FigoPinException which provides info about the account which should be created
     * @param newPin
     * 				new PIN
     * @return
     */
    public TaskStatusResponse setupAndSyncAccount(FigoPinException exception, String newPin) throws FigoException, IOException, FigoPinException, InterruptedException	{
    	return setupAndSyncAccount(exception.getBankCode(), exception.getCountryCode(), exception.getLoginName(), newPin);
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
     * Set new bank account sorting order
     * @param orderedList
     * 			List of accounts in the new order
     */
    public void setAccountOrder(List<Account> orderedList) throws FigoException, IOException	{
    	this.queryApi("/rest/accounts", new AccountOrderRequest(orderedList), "PUT", null);
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
     *            which offset into the result set should be used to determine the first transaction to return (useful in combination with count)
     * @param include_pending
     *            this flag indicates whether pending transactions should be included in the response; pending transactions are always included as a complete
     *            set, regardless of the `since` parameter
     * @return an array of Transaction objects
     */
    public List<Transaction> getTransactions(Account account, String since, Integer count, Integer offset, PendingTransactions include_pending) throws FigoException, IOException {
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
     *            which offset into the result set should be used to determine the first transaction to return (useful in combination with count)
     * @param include_pending
     *            this flag indicates whether pending transactions should be included in the response; pending transactions are always included as a complete
     *            set, regardless of the `since` parameter
     * @return an array of Transaction objects
     */
    public List<Transaction> getTransactions(String accountId, String since, Integer count, Integer offset, PendingTransactions include_pending) throws FigoException, IOException {
        String path = "";
        if (accountId == null) {
            path += "/rest/transactions?";
        } else {
            path += "/rest/accounts/" + accountId + "/transactions?";
        }
        if (since != null) {
            path += "since=" + URLEncoder.encode(since, "ISO-8859-1") + "&";
        }
        if (count != null) {
            path += "count=" + count + "&";
        }
        if (offset != null) {
            path += "offset=" + offset + "&";
        }
        if (include_pending != null) {
            path += "include_pending=" + (include_pending == PendingTransactions.INCLUDED ? "1" : "0");
        }
        Transaction.TransactionsResponse response = this.queryApi(path, null, "GET", Transaction.TransactionsResponse.class);
        return response == null ? Collections.<Transaction>emptyList() : response.getTransactions();
    }

    /**
     * Retrieve a specific transaction by ID
     * 
     * @param accountId
     *            ID of the account on which the transaction occurred
     * @param transactionId
     *            the figo ID of the specific transaction
     * @return Transaction or null
     */
    public Transaction getTransaction(String accountId, String transactionId) throws FigoException, IOException {
        return this.queryApi("/rest/accounts/" + accountId + "/transactions/" + transactionId, null, "GET", Transaction.class);
    }
    
    /**
     * Modifies the visited field of a specific transaction
     * @param transaction
     * 				transaction which will be modified
     * @param visited
     * 				new value for the visited field
     */
    public void modifyTransaction(Transaction transaction, FieldVisited visited) throws FigoException, IOException	{
    	this.queryApi("/rest/accounts/" + transaction.getAccountId() + "/transactions/" + transaction.getTransactionId(), new VisitedRequest(visited == FieldVisited.VISITED), "PUT", null);
    }
    
    
    /**
     * Modifies the visited field of all transactions of the current user
     * @param visited
     * 			new value for the visited field
     */
    public void modifyTransactions(FieldVisited visited) throws FigoException, IOException	{
    	this.queryApi("/rest/transactions", new VisitedRequest(visited == FieldVisited.VISITED), "PUT", null);
    }
    
    /**
     * Modifies the visited field of all transactions of a specific account
     * @param account
     * 			account which owns the transactions
     * @param visited
     * 			new value for the visited field
     */
    public void modifyTransactions(Account account, FieldVisited visited) throws FigoException, IOException	{
    	this.queryApi("/rest/accounts/" + account.getAccountId() + "/transactions", new VisitedRequest(visited == FieldVisited.VISITED), "PUT", null);
    }    
    
    /**
     * Modifies the visited field of all transactions of a specific account
     * @param accountId
     * 			Id of the account which owns the transactions
     * @param visited
     * 			new value for the visited field
     */
    public void modifyTransactions(String accountId, FieldVisited visited) throws FigoException, IOException	{
    	this.queryApi("/rest/accounts/" + accountId + "/transactions", new VisitedRequest(visited == FieldVisited.VISITED), "PUT", null);
    }

    /**
     * Removes a Transaction
     * @param transaction
     * 				transaction which will be removed
     */
    public void removeTransaction(Transaction transaction) throws FigoException, IOException	{
    	this.queryApi("/rest/accounts/" + transaction.getAccountId() + "/transactions/" + transaction.getTransactionId(), null, "DELETE", null);
    }
    
    /**
     * Retrieves a specific security
     * @param accountId
     * 			id of the security owning account
     * @param securityId
     * 			id of the security which will be retrieved
     * @return	Security or null
     */
    public Security getSecurity(String accountId, String securityId) throws FigoException, IOException	{
    	return this.queryApi("/rest/accounts/" + accountId + "/securities/" + securityId, null, "GET", Security.class);
    }
    
    /**
     * Retrieves a specific security
     * @param account
     * 			owning account
     * @param securityId
     * 			id of the security which will be retrieved
     * @return	Security or null
     */
    public Security getSecurity(Account account, String securityId) throws FigoException, IOException	{
    	return this.queryApi("/rest/accounts/" + account.getAccountId() + "/securities/" + securityId, null, "GET", Security.class);
    }
    
    /**
     * Retrieves all securities of the current user
     * @return List of Securities or null
     */
    public List<Security> getSecurities() throws FigoException, IOException	{
    	Security.SecurityResponse response = this.queryApi("/rest/securities", null, "GET", Security.SecurityResponse.class);
    	return response == null ? Collections.<Security>emptyList() : response.getSecurities();
    }
    
    /**
     * Retrieves all securities of a specific account
     * @param account Security owning account
     * @return List of Securities or null
     */
    public List<Security> getSecurities(Account account) throws FigoException, IOException	{
    	Security.SecurityResponse response = this.queryApi("/rest/accounts/" + account.getAccountId() + "/securities", null, "GET", Security.SecurityResponse.class);
    	return response == null ? Collections.<Security>emptyList() : response.getSecurities();
    }
    
    /**
     * Retrieves all securities of a specific account
     * @param accountId Security owning account id
     * @return List of Securities or null
     */
    public List<Security> getSecurities(String accountId) throws FigoException, IOException	{
    	Security.SecurityResponse response = this.queryApi("/rest/accounts/" + accountId + "/securities", null, "GET", Security.SecurityResponse.class);
    	return response == null ? Collections.<Security>emptyList() : response.getSecurities();
    }
    
    /**
     * Modifies the visited field of a specific security
     * @param security
     * 			security which will be modified
     * @param visited
     * 			new value for the visited field
     */
    public void modifySecurity(Security security, FieldVisited visited) throws FigoException, IOException	{
    	this.queryApi("/rest/accounts/" + security.getAccountId() + "/securities/" + security.getSecurityId(), new VisitedRequest(visited == FieldVisited.VISITED), "PUT", null);
    }
    
    /**
     * Modifies the visited field of all securities of the current user
     * @param visited
     * 			new value for the visited field
     */
    public void modifySecurities(FieldVisited visited) throws FigoException, IOException	{
    	this.queryApi("/rest/securities", new VisitedRequest(visited == FieldVisited.VISITED), "PUT", null);
    }
    
    /**
     * Modifies the visited field of all securities of a specific account
     * @param account
     * 			account which owns the securities
     * @param visited
     * 			new value for the visited field
     */
    public void modifySecurities(Account account, FieldVisited visited) throws FigoException, IOException	{
    	this.queryApi("/rest/accounts/" + account.getAccountId() + "/securities", new VisitedRequest(visited == FieldVisited.VISITED), "PUT", null);
    }
    
    /**
     * Modifies the visited field of all securities of a specific account
     * @param accountId
     * 			Id of the account which owns the securities
     * @param visited
     * 			new value for the visited field
     */
    public void modifySecurities(String accountId, FieldVisited visited) throws FigoException, IOException	{
    	this.queryApi("/rest/accounts/" + accountId + "/securities", new VisitedRequest(visited == FieldVisited.VISITED), "PUT", null);
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
        return response == null ? Collections.<Notification>emptyList() : response.getNotifications();
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
        return response == null ? Collections.<Payment>emptyList() : response.getPayments();
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
        return response == null ? Collections.<Payment>emptyList() : response.getPayments();
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
     * @param paymentId
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
    
    public Payment addContainerPayment(PaymentContainer container) throws FigoException, IOException	{
    	return this.queryApi("/rest/accounts/" + container.getAccountId() + "/payments", container, "POST", PaymentContainer.class);
    }
    
    /**
     * Returns a list of PaymentProposals
     */
    public List<PaymentProposal> getPaymentProposals() throws FigoException, IOException	{
    	PaymentProposalResponse response = this.queryApi("/rest/adress_book", null, "GET", PaymentProposalResponse.class);
    	return response == null ? Collections.<PaymentProposal>emptyList() : response.getPaymentProposals();
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
        return getApiEndpoint() + "/task/start?id=" + response.task_token;
    }

    /**
     * URL to trigger a synchronization. The user should open this URL in a web browser to synchronize his/her accounts with the respective bank servers. When
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
        return getApiEndpoint() + "/task/start?id=" + response.task_token;
    }
    
    /**
     * Get the current status of a Task
     * @param tokenResponse
     * 			A TaskTokenResponse Object for the task which will be checked
     * @return	A TaskStatusResponse Object with information about the task
     */
    public TaskStatusResponse getTaskState(TaskTokenResponse tokenResponse) throws FigoException, IOException	{
    	return this.queryApi("/task/progress?id=" + tokenResponse.task_token, new TaskStatusRequest(tokenResponse), "POST", TaskStatusResponse.class);
    }
    
    /**
     * Get the current status of a Task by id
     * @param tokenId
     * 			ID of the TaskToken which will be checked
     * @return	A TaskStatusResponse Object with information about the task.
     */
    public TaskStatusResponse getTaskState(String tokenId) throws FigoException, IOException	{
    	return this.queryApi("/task/progress?id=" + tokenId, new TaskStatusRequest(tokenId), "POST", TaskStatusResponse.class);
    }
    
    /**
     * Retrieves the current status of a Task and provide a PIN
     * @param tokenId
     * 			ID of the TaskToken which will be checked
     * @param pin
     * 			PIN which will be submitted
     * @return A TaskStatusResponse Object with information about the task.
     */
    public TaskStatusResponse getTaskState(String tokenId, String pin) throws FigoException, IOException	{
    	return this.queryApi("/task/progress?id=" + tokenId, new TaskStatusRequest(tokenId, pin), "POST", TaskStatusResponse.class);
    }

    /**
     * Start communication with bank server.
     * @param tokenResponse
     * 				TokenResponse Object
     */
    public void startTask(TaskTokenResponse tokenResponse) throws FigoException, IOException	{
    	this.queryApi("/task/start?id=" + tokenResponse.task_token, null, "GET", null);
    }
    
    /**
     * Start communication with bank server.
     * @param taskToken
     * 				Token ID
     */
    public void startTask(String taskToken) throws FigoException, IOException	{
    	this.queryApi("/task/start?id=" + taskToken, null, "GET", null);
    }
    
    /**
     * Cancels a given task if possible
     * @param tokenResponse
     * 				Token Response Object
     */
    public void cancelTask(TaskTokenResponse tokenResponse) throws FigoException, IOException	{
    	this.queryApi("/task/cancel?id=" + tokenResponse.task_token, null, "POST", null);
    }
    
    /**
     * Cancels a given task if possible
     * @param taskToken
     * 				Token ID
     */
    public void cancelTask(String taskToken) throws FigoException, IOException	{
    	this.queryApi("/task/cancel?id=" + taskToken, null, "POST", null);
    }
    
    public void startProcess(ProcessToken processToken) throws FigoException, IOException	{
    	this.queryApi("/process/start?id=" + processToken.getProcessToken(), null, "GET", null);
    }
    
    public ProcessToken createProcess(BusinessProcess process) throws FigoException, IOException	{
    	return this.queryApi("/client/process", process, "POST", ProcessToken.class);
    }
    
    @Override
    protected <T> T processResponse(HttpURLConnection connection, Type typeOfT) throws IOException, FigoException {
        // process response
        int code = connection.getResponseCode();
        if (code == 404) {
            return null;
        }
        return super.processResponse(connection, typeOfT);
    }
}
