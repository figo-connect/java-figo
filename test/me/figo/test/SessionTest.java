package me.figo.test;

import org.junit.*;
import me.figo.FigoSession;

public class SessionTest {
	
	FigoSession sut = null;
	
    @Before
    public void setUp() throws Exception {
    	sut = new FigoSession("ASHWLIkouP2O6_bgA2wWReRhletgWKHYjLqDaqb0LFfamim9RjexTo22ujRIP_cjLiRiSyQXyt2kM1eXU2XLFZQ0Hro15HikJQT_eNeT_9XQ");
    }

	@Test
    public void testGetAccount() {
        sut.getAccount("A1.2");
    }
}
