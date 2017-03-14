package com.leo.apediary.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 加密解密工具
 * 
 * @author leo
 */
public class EncryptUtils {
	private EncryptUtils() {}
	
	/**
	 * 用 MD5 加密(不可逆-加密密码)
	 * @param message
	 * @return 加密后的字符串
	 */
	public static String md5(String message) {
		try {
			MessageDigest md = MessageDigest.getInstance("md5");
			byte[] passwd = md.digest(message.getBytes());
			BASE64Encoder base64 = new BASE64Encoder();
			return base64.encode(passwd);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("加密失败~"+e.getMessage());
		}
	}
	
	/**
	 * 用 base64 加密(可逆-加密用户名)
	 * @param message
	 * @return 加密后的字符串
	 */
	public static String base64Encode(String message) {
		try {
			BASE64Encoder base64 = new BASE64Encoder();
			return base64.encode(message.getBytes());
		} catch (Exception e) {
			throw new RuntimeException("加密失败~"+e.getMessage());
		}
	}
	/**
	 * 用 base64 加密(可逆-加密用户名)
	 * @param message
	 * @return 加密后的字符串
	 */
	public static String base64Decode(String message) {
		try {
			BASE64Decoder base64 = new BASE64Decoder();
			return new String(base64.decodeBuffer(message));
		} catch (Exception e) {
			throw new RuntimeException("解密失败~"+e.getMessage());
		}
	}
}
