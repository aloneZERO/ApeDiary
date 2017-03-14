package com.leo.apediary.util;

import java.math.BigInteger;
import java.util.Random;

/**
 * 随机生成32位 GUID
 * 字符大写
 * @author leo
 */
public class IdGenertor {
	public static String genGUID() {
		return new BigInteger(165, new Random()).toString(36);
	}
	
	private IdGenertor() {}
}
