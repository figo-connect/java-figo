package me.figo;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import me.figo.internal.TaskStatusResponse;
import me.figo.internal.TaskTokenResponse;
import me.figo.internal.TokenResponse;
import me.figo.models.Account;
import me.figo.models.BusinessProcess;
import me.figo.models.LoginSettings;
import me.figo.models.ProcessOption;
import me.figo.models.ProcessStep;
import me.figo.models.ProcessToken;
import me.figo.models.Service;
import me.figo.models.Transaction;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class WritingTest {

	private final static String USER = "testuser@example.com";
	private final static String PASSWORD = "some_words";
	
	// Bank account informations needed
	private final String ACCOUNT = "figo";
	private final String BANKCODE = "90090042";
	private final String PIN = "figo";
	
	private static String rand = null;
	
	
	private static FigoConnection fc;
	private static TokenResponse accessToken;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		SecureRandom random = new SecureRandom();
		rand = new BigInteger(130, random).toString(32);
		fc = new FigoConnection(System.getenv("FIGO_CLIENT_ID"), System.getenv("FIGO_CLIENT_SECRET"),
				"https://127.0.0.1/");
		String response = fc.addUser("Test", rand + USER, PASSWORD, "de");
		assertTrue(response.length() == 19);
		accessToken = WritingTest.fc.credentialLogin(rand + USER, PASSWORD);
		assertTrue(accessToken.access_token instanceof String);	
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		FigoSession fs = new FigoSession(accessToken.access_token);
		fs.removeUser();
	}
	
	@Test
	public void test_getSupportedPaymentServices() throws FigoException, IOException	{
		FigoSession fs = new FigoSession(accessToken.access_token);
		List<Service> response = fs.getSupportedServices("de");
		assertTrue(response.size() >= 22);
	}
	
	@Test
	public void test_getLoginSettings() throws IOException, FigoException	{
		FigoSession fs = new FigoSession(accessToken.access_token);
		LoginSettings response = fs.getLoginSettings("de", "47251550");
		assertTrue(response instanceof LoginSettings);		
	}
	
	public void test_addBankAccount() throws FigoException, IOException	{
		FigoSession fs = new FigoSession(accessToken.access_token);
		TaskTokenResponse response= fs.setupNewAccount(BANKCODE, "de", ACCOUNT, PIN, Arrays.asList("standingOrders"), true, false);
		TaskStatusResponse taskStatus = fs.getTaskState(response);
		assertTrue(taskStatus instanceof TaskStatusResponse);
		try {
			Thread.sleep(25000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertTrue(fs.getAccounts().size() == 1);
	}
	
	public void test_modifyTransaction() throws IOException, FigoException	{
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
	
	public void test_modifyAccountTransactions() throws FigoException, IOException	{
		FigoSession fs = new FigoSession(accessToken.access_token);
		Account testAccount = fs.getAccounts().get(0);
		fs.modifyTransactions(testAccount, FigoSession.FieldVisited.NOT_VISITED);
		Transaction testTransaction = fs.getTransactions(testAccount).get(4);
		assertFalse(testTransaction.isVisited());
		fs.modifyTransactions(testAccount, FigoSession.FieldVisited.VISITED);
		testTransaction = fs.getTransactions().get(4);
		assertTrue(testTransaction.isVisited());
	}
	
	public void test_deleteTransaction() throws IOException, FigoException	{
		FigoSession fs = new FigoSession(accessToken.access_token);
		List<Transaction> transactions = fs.getTransactions();
		fs.removeTransaction(transactions.get(0));
		assertTrue(transactions.size() > fs.getTransactions().size());
		fs.removeUser();
	}
}
