package org.ketab.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import sun.misc.BASE64Encoder;

public class Utils {
	
	public String encrypt(String pw) throws NoSuchAlgorithmException, UnsupportedEncodingException{
		MessageDigest md = MessageDigest.getInstance("SHA");
		md.update(pw.getBytes());
		byte[] rawPW = md.digest();
		return (new BASE64Encoder()).encode(rawPW);
	}
}
