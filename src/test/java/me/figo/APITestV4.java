package me.figo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import me.figo.internal.Credentials;
import me.figo.internal.StartProviderSyncResponse;
import me.figo.internal.TaskStatusResponse;
import me.figo.internal.TaskTokenResponse;
import me.figo.internal.TokenResponse;
import me.figo.models.Access;
import me.figo.models.Account;
import me.figo.models.ChallengeV4;
import me.figo.models.Consent;
import me.figo.models.LoginSettings;
import me.figo.models.Service;
import me.figo.models.Transaction;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class APITestV4 {

	private final static String USER = "testuser@example.com";
	private final static String PASSWORD = "some_words";
	
	private final String BANKCODE = "90090042";
	private final String ACCOUNT = "figo";
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
		assertTrue(response.length() == 0);
		accessToken = APITestV4.fc.credentialLogin(rand + USER, PASSWORD);
		assertTrue(accessToken.access_token instanceof String);
		Object catalog = fc.getCatalog();
		assertNotNull(catalog);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		FigoSession fs = new FigoSession(accessToken.access_token);
		fs.removeUser();
	}
	
	@Test
	public void test_getVersion() throws FigoException, IOException {
		FigoSession fs = new FigoSession(accessToken.access_token);
		Object version = fs.getVersion();
		assertNotNull(version);
	}

	@Test
	public void test_getAccesses() throws FigoException, IOException {
		FigoSession fs = new FigoSession(accessToken.access_token);
		List<Access> accesses = fs.getAccesses();
		assertTrue(accesses.size() >= 2);
		String accessId = accesses.get(0).getId();
		Access access = fs.getAccess(accessId);
		assertNotNull(access);
		StartProviderSyncResponse startProviderSync = fs.startProviderSync(accessId, "4711", "http://localhost", false,
				false,
				"foo", "bar");
		assertNotNull(startProviderSync);
		String syncId = startProviderSync.getId();
		List<ChallengeV4> syncChallenges = fs.getSyncChallenges(accessId, syncId);
		assertTrue(syncChallenges.size() > 0);
		ChallengeV4 challenge = syncChallenges.get(0);
		String accessMethodId = access.getAccessMethodId();
		// ChallengeV4 syncChallenge = fs.getSyncChallenge(accessId, syncId,
		// challenge.getId().toString());
		// assertNotNull(syncChallenge);
		// fs.solveSyncChallenge(accessId, syncId, challenge.getId().toString(),
		// accessMethodId);
		Consent consent = new Consent(false, 1, Arrays.asList(new String[] { "ACCOUNTS", "BALANCES" }), new Date());
		Credentials credentials = new Credentials("foo", "bar");
		Access access2 = fs.addProviderAccess(accessMethodId, "account1", "EUR", false, credentials, consent);
		assertNotNull(access2);
		Object accessWithRemovePin = fs.removeStoredPin(accessId);
		assertNotNull(accessWithRemovePin);
	}

	public void test_getSupportedPaymentServices() throws FigoException, IOException	{
		FigoSession fs = new FigoSession(accessToken.access_token);
		List<Service> response = fs.getSupportedServices("de");
		assertTrue(response.size() >= 22);
	}
	
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
	}
	
	public void testWrongCredentialsErrorMessage() throws FigoException, IOException	{
		FigoSession fs = new FigoSession(accessToken.access_token);
		
		//Credentials must be a list of at least two string, just ["foo"] should fail
		List<String> wrong_credentials = Arrays.asList("foo");
		try {
			TaskTokenResponse response= fs.setupNewAccount(null, "de", wrong_credentials, null);			
		} catch (FigoException e){
			assertEquals("Request body doesn't match input schema.", e.getErrorDescription());			
		}
	}
	
	public void testGetErrorMessage() throws IOException {
		try {
			FigoSession fs = new FigoSession(accessToken.access_token);
			Account acc = fs.getAccount("666");
			fail(acc.getName());
		} catch (FigoException e) {
			assertEquals("The requested object does not exist.", e.getErrorDescription());
		}
	}
}