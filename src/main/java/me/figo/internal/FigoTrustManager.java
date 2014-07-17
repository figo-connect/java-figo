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

public class FigoTrustManager implements X509TrustManager {

    private static final char[]       hexDigits          = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

    private static List<String> VALID_FINGERPRINTS = new ArrayList<String>(Arrays.asList("3A:62:54:4D:86:B4:34:38:EA:34:64:4E:95:10:A9:FF:37:27:69:C0",
                                                                 "CF:C1:BC:7F:6A:16:09:2B:10:83:8A:B0:22:4F:3A:65:D2:70:D7:3E"));

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
    
    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
        return null;
    }

    public void checkClientTrusted(X509Certificate[] certs, String authType) throws CertificateException {
    }

    public void checkServerTrusted(X509Certificate[] certs, String authType) throws CertificateException {
        if (certs.length == 0) {
            throw new CertificateException("No certificate found");
        } else {
            String thumbprint = getThumbPrint(certs[0]);
            if (!VALID_FINGERPRINTS.contains(thumbprint))
                throw new CertificateException();
        }
    }

    private static String getThumbPrint(X509Certificate cert) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] der = cert.getEncoded();
            md.update(der);
            byte[] digest = md.digest();
            return hexify(digest);
        } catch (NoSuchAlgorithmException e) {
            return "";
        } catch (CertificateEncodingException e) {
            return "";
        }
    }

    private static String hexify(byte bytes[]) {
        StringBuffer buf = new StringBuffer(bytes.length * 2);

        for (int i = 0; i < bytes.length; ++i) {
            buf.append(hexDigits[(bytes[i] & 0xf0) >> 4]);
            buf.append(hexDigits[bytes[i] & 0x0f]);
            if (i + 1 < bytes.length)
                buf.append(":");
        }

        return buf.toString();
    }
}
