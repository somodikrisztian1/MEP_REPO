package hu.mep.utils.others;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Encoder {

	public static String encodePasswordWithMD5(String originalPassword) {

		final String MD5 = "MD5";
		try {
			MessageDigest digest = java.security.MessageDigest.getInstance(MD5);
			digest.update(originalPassword.getBytes());
			byte messageDigest[] = digest.digest();

			StringBuilder hexString = new StringBuilder();
			for (byte aMessageDigest : messageDigest) {
				String h = Integer.toHexString(0xFF & aMessageDigest);
				while (h.length() < 2)
					h = "0" + h;
				hexString.append(h);
			}
			return hexString.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
	}
	
}
