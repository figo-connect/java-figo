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
            "AD:A0:E3:2B:1F:CE:E8:44:F2:83:BA:AE:E4:7D:F2:AD:44:48:7F:1E");

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
