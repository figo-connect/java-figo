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

package me.figo.internal;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.net.ssl.X509TrustManager;

public class FigoTrustManager implements X509TrustManager {

    private static final List<String> VALID_FINGERPRINTS = new ArrayList<String>(Arrays.asList(
            "070F14AEB94AFB3DF800E82B69A8515CEED2F5B1BA897BEF6432458F61CF9E33", 
	    "79B2A29300853B0692B1B5F2247948583AA5220FC5CDE9499AC8451EDBE0DA50"));

    /**
     * @return the list of trusted certificate fingerprints using SHA256
     */
    public static List<String> getTrustedFingerprints() {
        return VALID_FINGERPRINTS;
    }
    
    /**
     * Add a fingerprint to the trusted list, e.g. when using a custom figo deployment.
     * 
     * @param fingerprint the SHA256 hash of the SSL certificate in upper case
     */
    public static void addTrustedFingerprint(String fingerprint) {
        VALID_FINGERPRINTS.add(fingerprint);
    }
    
    @Override
    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
        return null;
    }

    @Override
    public void checkClientTrusted(X509Certificate[] certs, String authType) throws CertificateException {
    }

    @Override
    public void checkServerTrusted(X509Certificate[] certs, String authType) throws CertificateException {
        if (certs.length == 0) {
            throw new CertificateException("No certificate found");
        } else {
			String thumbprint = getThumbPrint(certs[0]);
			if (!VALID_FINGERPRINTS.contains(thumbprint) && !getFingerprintsFromEnv().contains(thumbprint)) {
                throw new CertificateException("Fingerprint does not match certificate");
            }
        }
    }

    private static String getThumbPrint(X509Certificate cert) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] der = cert.getEncoded();
            md.update(der);
            byte[] digest = md.digest();
			return new BigInteger(1, digest).toString(16).toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            return "";
        } catch (CertificateEncodingException e) {
            return "";
        }
    }

    private static List<String> getFingerprintsFromEnv()    {
        String fingerprintList = System.getenv("FIGO_API_FINGERPRINTS");
        if(fingerprintList!=null)
        	return Arrays.asList(fingerprintList.split(":"));
        else
        	return new ArrayList<>();
    }
}
