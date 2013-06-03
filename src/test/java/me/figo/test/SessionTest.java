package me.figo.test;

import java.io.IOException;
import java.util.List;

import org.junit.*;

import static org.junit.Assert.*;
import me.figo.FigoException;
import me.figo.FigoSession;
import me.figo.models.Account;
import me.figo.models.AccountBalance;
import me.figo.models.Notification;
import me.figo.models.Transaction;

public class SessionTest {
	
	FigoSession sut = null;
	
    @Before
    public void setUp() throws Exception {
    	sut = new FigoSession("ASHWLIkouP2O6_bgA2wWReRhletgWKHYjLqDaqb0LFfamim9RjexTo22ujRIP_cjLiRiSyQXyt2kM1eXU2XLFZQ0Hro15HikJQT_eNeT_9XQ");
    }
    
	@Test
    public void testGetAccount() throws FigoException, IOException {
        Account a = sut.getAccount("A1.2");
        assertEquals(a.getAccountId(), "A1.2");
        
        AccountBalance b = sut.getAccountBalance(a);
        assertNotNull(b.getBalance());
        assertNotNull(b.getBalanceDate());
        
        List<Transaction> ts = sut.getTransactions(a);
        assertTrue(ts.size() > 0);
    }
	
	@Test
	public void testErrorHandling() throws IOException {
		try {
			sut.getAccount("A1.3");
		} catch (FigoException e) {
			assertNotNull(e.getErrorCode());
			return;
		}
		
		fail();
	}
	
	@Test
	public void testGetTransactions() throws FigoException, IOException {
		List<Transaction> transactions = sut.getTransactions();
		assertTrue(transactions.size() > 0);
	}
	
	@Test
	public void testGetNotifications() throws FigoException, IOException {
		List<Notification> notifications = sut.getNotifications();
		assertTrue(notifications.size() > 0);
	}
	
	@Test
	public void testCreateUpdateDeleteNotification() throws FigoException, IOException {
		Notification notification = new Notification("/rest/transactions", "http://figo.me/test", "qwe");
		
		Notification addedNotificaton = sut.addNotification(notification);
		assertNotNull(addedNotificaton.getNotificationId());
		assertEquals(addedNotificaton.getObserveKey(), "/rest/transactions");
		assertEquals(addedNotificaton.getNotifyURI(), "http://figo.me/test");
		assertEquals(addedNotificaton.getState(), "qwe");
		
		addedNotificaton.setState("asd");
		Notification updatedNotification = sut.updateNotification(addedNotificaton);
		assertEquals(updatedNotification.getNotificationId(), addedNotificaton.getNotificationId());
		assertEquals(updatedNotification.getObserveKey(), "/rest/transactions");
		assertEquals(updatedNotification.getNotifyURI(), "http://figo.me/test");
		assertEquals(updatedNotification.getState(), "asd");

		sut.removeNotification(updatedNotification);
	}
	
	@Test
	public void testSyncUri() throws FigoException, IOException {
		assertNotNull(sut.getSyncURL("qwe", "http://figo.me/test"));
	}
}
