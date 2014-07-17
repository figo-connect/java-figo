package me.figo;

import static org.junit.Assert.*;
import me.figo.internal.FigoTrustManager;

import org.junit.Test;

public class TrustManagerTest {
    
    @Test
    public void testAddingFingerprint() {
        assertFalse(FigoTrustManager.getTrustedFingerprints().contains("dummy"));
        FigoTrustManager.addTrustedFingerprint("dummy");
        assertTrue(FigoTrustManager.getTrustedFingerprints().contains("dummy"));
    }
}
