package me.figo;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import me.figo.internal.TaskStatusResponse;
import me.figo.internal.TokenResponse;
import me.figo.models.Account;
import me.figo.models.LoginSettings;
import me.figo.models.Service;
import me.figo.models.Transaction;
import me.figo.internal.TaskTokenResponse;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class WritingTest {

	private String CLIENT_ID = "C-9rtYgOP3mjHhw0qu6Tx9fgk9JfZGmbMqn-rnDZnZwI";
	private String CLIENT_SECRET = "Sv9-vNfocFiTe_NoMRkvNLe_jRRFeESHo8A0Uhyp7e28";
	private String USER = "testuser@test.de";
	private String PASSWORD = "some_words";
	
	// Bank account infos needed
	private String ACCOUNT = "";
	private String BANKCODE = "";
	private String PIN = "";
	
	private FigoConnection fc = new FigoConnection(CLIENT_ID, CLIENT_SECRET, "https://127.0.0.1/");
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}
	
	public void test_01_addUser() throws IOException, FigoException {
		String response = this.fc.addUser("Test", USER, PASSWORD, "de");
		assertTrue(response.length() == 19);
	}
	
	@Test
	public void test_010_addUserAndLogin() throws IOException, FigoException	{
		FigoSession fs = new FigoSession(this.fc.addUserAndLogin("Test", USER, PASSWORD, "de").access_token);
		assertTrue(fs instanceof FigoSession);
	}

	@Test
	public void test_02_credentialLogin() throws FigoException, IOException {
		TokenResponse accessToken = this.fc.credentialLogin(USER, PASSWORD);
		assertTrue(accessToken.access_token instanceof String);				
	}	
	
	@Test
	public void test_03_getSupportedPaymentServices() throws FigoException, IOException	{
		TokenResponse accessToken = this.fc.credentialLogin(USER, PASSWORD);
		FigoSession fs = new FigoSession(accessToken.access_token);
		List<Service> response = fs.getSupportedServices("de");
		assertTrue(response.size() == 21);
	}
	
	@Test
	public void test_04_getLoginSettings() throws IOException, FigoException	{
		TokenResponse accessToken = this.fc.credentialLogin(USER, PASSWORD);
		FigoSession fs = new FigoSession(accessToken.access_token);
		LoginSettings response = fs.getLoginSettings("de", "47251550");
		assertTrue(response instanceof LoginSettings);		
	}
	
	public void test_05_addBankAccount() throws FigoException, IOException	{
		TokenResponse accessToken = this.fc.credentialLogin(USER, PASSWORD);
		FigoSession fs = new FigoSession(accessToken.access_token);
		TaskTokenResponse response= fs.setupNewAccount(BANKCODE, "de", ACCOUNT, PIN);
		TaskStatusResponse taskStatus = fs.getTaskState(response);
		assertTrue(taskStatus instanceof TaskStatusResponse);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertTrue(fs.getAccounts().size() == 1);
	}
	
	@Test(expected=FigoPinException.class)
	public void test_050_addBankAccountAndSyncWithWrongPin() throws IOException, FigoException, FigoPinException, InterruptedException	{
		TokenResponse accessToken = this.fc.credentialLogin(USER, PASSWORD);
		FigoSession fs = new FigoSession(accessToken.access_token);
		fs.setupAndSyncAccount(BANKCODE, "de", ACCOUNT, "123456");
		Thread.sleep(5000);
		assertTrue(fs.getAccounts().size() == 0);
	}
	
	@Test
	public void test_051_addBankAccountAndSyncWithWrongAndCorrectPin() throws IOException, FigoException, FigoPinException, InterruptedException	{
		TokenResponse accessToken = this.fc.credentialLogin(USER, PASSWORD);
		FigoSession fs = new FigoSession(accessToken.access_token);
		try	{
			fs.setupAndSyncAccount(BANKCODE, "de", ACCOUNT, "123456");
		} catch (FigoPinException e)	{
			fs.setupAndSyncAccount(e, PIN);
		}
		Thread.sleep(5000);
		assertTrue(fs.getAccounts().size() == 1);
	}
	
	
	public void test_052_addBankAccountAndSync() throws IOException, FigoException, FigoPinException, InterruptedException	{
		TokenResponse accessToken = this.fc.credentialLogin(USER, PASSWORD);
		FigoSession fs = new FigoSession(accessToken.access_token);
		fs.setupAndSyncAccount(BANKCODE, "de", ACCOUNT, PIN);
		Thread.sleep(5000);
		assertTrue(fs.getAccounts().size() == 1);
	}
	
	@Test
	public void test_06_modifyTransaction() throws IOException, FigoException	{
		TokenResponse accessToken = this.fc.credentialLogin(USER, PASSWORD);
		FigoSession fs = new FigoSession(accessToken.access_token);
		Account testAccount = fs.getAccounts().get(0);
		Transaction testTransaction = fs.getTransactions(testAccount).get(0);
		String transactionId = testTransaction.getTransactionId();
		fs.modifyTransaction(testTransaction, false);
		testTransaction = fs.getTransaction(testAccount.getAccountId(), transactionId);
		assertFalse(testTransaction.isVisited());
		fs.modifyTransaction(testTransaction, true);
		testTransaction = fs.getTransaction(testAccount.getAccountId(), transactionId);
		assertTrue(testTransaction.isVisited());		
	}
	
	@Test
	public void test_07_modifyAccountTransactions() throws FigoException, IOException	{
		TokenResponse accessToken = this.fc.credentialLogin(USER, PASSWORD);
		FigoSession fs = new FigoSession(accessToken.access_token);
		Account testAccount = fs.getAccounts().get(0);
		fs.modifyTransactions(testAccount, false);
		Transaction testTransaction = fs.getTransactions(testAccount).get(4);
		assertFalse(testTransaction.isVisited());
		fs.modifyTransactions(testAccount, true);
		testTransaction = fs.getTransactions().get(4);
		assertTrue(testTransaction.isVisited());
	}
	
	@Test
	public void test_08_modifyUserTransaction() throws FigoException, IOException	{
		TokenResponse accessToken = this.fc.credentialLogin(USER, PASSWORD);
		FigoSession fs = new FigoSession(accessToken.access_token);
		fs.modifyTransactions(false);
		Transaction testTransaction = fs.getTransactions().get(3);
		assertFalse(testTransaction.isVisited());
		fs.modifyTransactions(true);
		testTransaction = fs.getTransactions().get(3);
		assertTrue(testTransaction.isVisited());
	}
	
	@Test
	public void test_09_deleteTransaction() throws IOException, FigoException	{
		TokenResponse accessToken = this.fc.credentialLogin(USER, PASSWORD);
		FigoSession fs = new FigoSession(accessToken.access_token);
		List<Transaction> transactions = fs.getTransactions();
		fs.removeTransaction(transactions.get(0));
		assertTrue(transactions.size() > fs.getTransactions().size());
		fs.removeUser();
	}
	

}
