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

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.net.ssl.X509TrustManager;
import org.apache.commons.codec.binary.Hex;

public class FigoTrustManager implements X509TrustManager {

    private static final List<String> VALID_FINGERPRINTS = new ArrayList<>(Arrays.asList(
            // api.figo.me
            "DBE2E9158FC9903084FE36CAA61138D85A205D93"
    ));

    /**
     * @return the list of trusted certificate fingerprints using SHA1
     */
    public static List<String> getTrustedFingerprints() {
        return VALID_FINGERPRINTS;
    }
    
    /**
     * Add a fingerprint to the trusted list, e.g. when using a custom figo deployment.
     * 
     * @param fingerprint the SHA1 hash of the SSL certificate in upper case
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
            if (!VALID_FINGERPRINTS.contains(thumbprint) && !getFingerprintsFromEnv().contains(thumbprint)){
                throw new CertificateException("Untrusted certificate fingerprint");
            }
        }
    }

    private static String getThumbPrint(X509Certificate cert) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] der = cert.getEncoded();
            md.update(der);
            byte[] digest = md.digest();
            return new String(Hex.encodeHex(digest, false));
        } catch (NoSuchAlgorithmException e) {
            return "";
        } catch (CertificateEncodingException e) {
            return "";
        }
    }

    private static List<String> getFingerprintsFromEnv()    {
        String fingerprintList = System.getenv("FIGO_API_FINGERPRINTS");
        if (fingerprintList == null) {
            return new ArrayList<>();
        } else {
            return Arrays.asList(fingerprintList.toUpperCase().split(":"));
        }
    }
}
