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
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import me.figo.internal.FakeTrustManager;
import me.figo.internal.TokenResponse;
import me.figo.models.Account;
import me.figo.models.Notification;
import me.figo.models.Payment;
import me.figo.models.PaymentProposal;
import me.figo.models.PaymentType;
import me.figo.models.Security;
import me.figo.models.StandingOrder;
import me.figo.models.TanScheme;
import me.figo.models.Transaction;
import me.figo.models.User;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

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
    }

    @Test
    public void testGetAccountBalance() throws FigoException, IOException	{
    	Account a = sut.getAccount("A1.2");
        assertNotNull(a.getBalance().getBalance());
        assertNotNull(a.getBalance().getBalanceDate());
    }

    @Test
    public void testGetAccountTransactions() throws FigoException, IOException	{
    	Account a = sut.getAccount("A1.2");
        List<Transaction> ts = sut.getTransactions(a);
        assertTrue(ts.size() > 0);
    }

    @Test
    public void testGetAccountPayments() throws FigoException, IOException	{
    	Account a = sut.getAccount("A1.2");
        List<Payment> ps = sut.getPayments(a);
        assertTrue(ps.size() >= 0);
    }
    
    @Test
    public void testGetSupportedTanSchemes() throws FigoException, IOException	{
    	Account a = sut.getAccount("A1.1");
    	List<TanScheme> schemes = a.getSupportedTanSchemes();
    	assertTrue(schemes.size() == 4);
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
    public void testGetPayments() throws FigoException, IOException {
        List<Payment> payments = sut.getPayments();
        assertTrue(payments.size() >= 0);
    }

    @Test(expected=FigoException.class)
    public void testExceptionHandling() throws IOException, FigoException {
        sut.getSyncURL("", "http://localhost:3003/");
    }

    @Test
    public void testSyncUri() throws FigoException, IOException {
        assertNotNull(sut.getSyncURL("qwe", "http://figo.me/test"));
    }

    @Test
    public void testUser() throws FigoException, IOException {
        User user = sut.getUser();
        assertEquals("demo@figo.me", user.getEmail());
    }
    
    @Test
    public void testGetSupportedPaymentTypes() throws FigoException, IOException	{
    	HashMap<String, PaymentType> types = sut.getAccounts().get(0).getSupportedPaymentTypes();
    	assertEquals(2, types.size());
    }
    
    @Test
	public void testGetStandingOrders() throws IOException, FigoException {
        List<StandingOrder> so = sut.getStandingOrders();
        assertTrue(so.size() >= 0);
	}

    @Test
    public void testGetErrorMessage() throws IOException {
        try {
            Account acc = sut.getAccount("666");
            fail(acc.getName());
        }
        catch(FigoException e)  {
            assertEquals("Entry not found.", e.getErrorMessage());
            assertEquals(null, e.getErrorDescription());
        }
    }

    @Test
    public void testFakeTrustManager() throws IOException, FigoException {
        FakeTrustManager fakeTrustManager = new FakeTrustManager();
        sut.setTrustManager(fakeTrustManager);
        User user = sut.getUser();
        assertEquals("demo@figo.me", user.getEmail());
    }

}
