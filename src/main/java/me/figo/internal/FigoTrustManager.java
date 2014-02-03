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
import java.util.Arrays;
import java.util.List;

import javax.net.ssl.X509TrustManager;

public class FigoTrustManager implements X509TrustManager {

	private static final char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
	
	private static final List<String> VALID_FINGERPRINTS = Arrays.asList(
			"A6:FE:08:F4:A8:86:F9:C1:BF:4E:70:0A:BD:72:AE:B8:8E:B7:78:52",
			"AD:A0:E3:2B:1F:CE:E8:44:F2:83:BA:AE:E4:7D:F2:AD:44:48:7F:1E",
			"E0:46:84:06:D0:1B:0B:6E:3D:3F:7F:A4:F5:D7:32:C2:56:BA:2F:0A");

	public java.security.cert.X509Certificate[] getAcceptedIssuers() {
		return null;
	}

	public void checkClientTrusted(X509Certificate[] certs, String authType) throws CertificateException {
	}

	public void checkServerTrusted(X509Certificate[] certs, String authType) throws CertificateException {
		if(certs.length == 0) {
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
			if (i+1 < bytes.length)
				buf.append(":");
		}

		return buf.toString();
	}
}
