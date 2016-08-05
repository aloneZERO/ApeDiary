package com.leo.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import sun.misc.BASE64Encoder;

public class Md5Util {
	
	private Md5Util() {
		super();
	}
	
	/**
	 * 加密指定字符串，加密方式：MD5
	 * @param s
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public static String encrypteByMd5(String s) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		BASE64Encoder base64En = new BASE64Encoder();
		return base64En.encode(md5.digest(s.getBytes("utf-8")));
	}
	
	/**
	 * 加密测试方法
	 * @param args
	 * @throws UnsupportedEncodingException 
	 * @throws NoSuchAlgorithmException 
	 */
	public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		System.out.println(Md5Util.encrypteByMd5("233"));
	}
}
