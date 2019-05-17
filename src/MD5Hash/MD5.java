/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MD5Hash;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Ghita David Leonard
 */
public class MD5 {

	public static String getHash(String plaintext) throws NoSuchAlgorithmException {
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		md5.reset();
		md5.update(plaintext.getBytes());

		byte[] digest = md5.digest();
		BigInteger bigInt = new BigInteger(1, digest);
		String hashtext = bigInt.toString(16);

		// If the generated hash is less than 32 characters we add 0 to complete it
		while (hashtext.length() < 32) {
			hashtext = "0" + hashtext;
		}

//		System.out.println(hashtext);
		return hashtext;
	}

	public static boolean testPassword(String plaintext, String hash) throws NoSuchAlgorithmException {
		return getHash(plaintext).equals(hash);
	}

}
