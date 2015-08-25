package me.figo;

import static org.junit.Assert.*;

import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;

import me.figo.internal.TaskStatusResponse;
import me.figo.internal.TokenResponse;
import me.figo.models.Account;
import me.figo.models.LoginSettings;
import me.figo.models.Service;
import me.figo.models.StandingOrder;
import me.figo.models.Transaction;
import me.figo.internal.TaskTokenResponse;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class WritingTest {

	private final String CLIENT_ID = "CaESKmC8MAhNpDe5rvmWnSkRE_7pkkVIIgMwclgzGcQY";
	private final String CLIENT_SECRET = "STdzfv0GXtEj_bwYn7AgCVszN1kKq5BdgEIKOM_fzybQ";
	private final String USER = "testuser@test.de";
	private final String PASSWORD = "some_words";
	
	// Bank account infos needed
	private final String ACCOUNT = "figo";
	private final String BANKCODE = "90090042";
	private final String PIN = "figo";
	
	private static String rand = null;
	
	
	private final FigoConnection fc = new FigoConnection(CLIENT_ID, CLIENT_SECRET, "https://127.0.0.1/");
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		SecureRandom random = new SecureRandom();
		rand = new BigInteger(130, random).toString(32); 
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}
	
	public void test_01_addUser() throws IOException, FigoException {
		String response = this.fc.addUser("Test", rand+USER, PASSWORD, "de");
		assertTrue(response.length() == 19);
	}

	public void test_02_credentialLogin() throws FigoException, IOException {
		TokenResponse accessToken = this.fc.credentialLogin(USER, PASSWORD);
		assertTrue(accessToken.access_token instanceof String);				
	}	
	
	public void test_03_getSupportedPaymentServices() throws FigoException, IOException	{
		TokenResponse accessToken = this.fc.credentialLogin(USER, PASSWORD);
		FigoSession fs = new FigoSession(accessToken.access_token);
		List<Service> response = fs.getSupportedServices("de");
		assertTrue(response.size() == 22);
	}
	
	public void test_04_getLoginSettings() throws IOException, FigoException	{
		TokenResponse accessToken = this.fc.credentialLogin(USER, PASSWORD);
		FigoSession fs = new FigoSession(accessToken.access_token);
		LoginSettings response = fs.getLoginSettings("de", "47251550");
		assertTrue(response instanceof LoginSettings);		
	}
	
	public void test_05_addBankAccount() throws FigoException, IOException	{
		TokenResponse accessToken = this.fc.credentialLogin(USER, PASSWORD);
		FigoSession fs = new FigoSession(accessToken.access_token);
		TaskTokenResponse response= fs.setupNewAccount(BANKCODE, "de", ACCOUNT, PIN, Arrays.asList("standingOrders"));
		TaskStatusResponse taskStatus = fs.getTaskState(response);
		assertTrue(taskStatus instanceof TaskStatusResponse);
		try {
			Thread.sleep(25000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertTrue(fs.getAccounts().size() == 1);
	}
	
	public void test_06_modifyTransaction() throws IOException, FigoException	{
		TokenResponse accessToken = this.fc.credentialLogin(USER, PASSWORD);
		FigoSession fs = new FigoSession(accessToken.access_token);
		Account testAccount = fs.getAccounts().get(0);
		Transaction testTransaction = fs.getTransactions(testAccount).get(0);
		String transactionId = testTransaction.getTransactionId();
		fs.modifyTransaction(testTransaction, FigoSession.FieldVisited.NOT_VISITED);
		testTransaction = fs.getTransaction(testAccount.getAccountId(), transactionId);
		assertFalse(testTransaction.isVisited());
		fs.modifyTransaction(testTransaction, FigoSession.FieldVisited.VISITED);
		testTransaction = fs.getTransaction(testAccount.getAccountId(), transactionId);
		assertTrue(testTransaction.isVisited());		
	}
	
	public void test_07_modifyAccountTransactions() throws FigoException, IOException	{
		TokenResponse accessToken = this.fc.credentialLogin(USER, PASSWORD);
		FigoSession fs = new FigoSession(accessToken.access_token);
		Account testAccount = fs.getAccounts().get(0);
		fs.modifyTransactions(testAccount, FigoSession.FieldVisited.NOT_VISITED);
		Transaction testTransaction = fs.getTransactions(testAccount).get(4);
		assertFalse(testTransaction.isVisited());
		fs.modifyTransactions(testAccount, FigoSession.FieldVisited.VISITED);
		testTransaction = fs.getTransactions().get(4);
		assertTrue(testTransaction.isVisited());
	}
	
	public void test_08_modifyUserTransaction() throws FigoException, IOException	{
		TokenResponse accessToken = this.fc.credentialLogin(USER, PASSWORD);
		FigoSession fs = new FigoSession(accessToken.access_token);
		fs.modifyTransactions(FigoSession.FieldVisited.NOT_VISITED);
		Transaction testTransaction = fs.getTransactions().get(3);
		assertFalse(testTransaction.isVisited());
		fs.modifyTransactions(FigoSession.FieldVisited.VISITED);
		testTransaction = fs.getTransactions().get(3);
		assertTrue(testTransaction.isVisited());
	}
	
	public void test_09_deleteTransaction() throws IOException, FigoException	{
		TokenResponse accessToken = this.fc.credentialLogin(USER, PASSWORD);
		FigoSession fs = new FigoSession(accessToken.access_token);
		List<Transaction> transactions = fs.getTransactions();
		fs.removeTransaction(transactions.get(0));
		assertTrue(transactions.size() > fs.getTransactions().size());
		fs.removeUser();
	}
}
