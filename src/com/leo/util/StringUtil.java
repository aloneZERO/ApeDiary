package com.leo.util;

/**
 * 字符串工具类
 * @author leo
 *
 */
public class StringUtil {
	
	private StringUtil() {
		// do nothing
	}
	
	/**
	 * 字符串是否为空
	 * 注意：str==null 一定要写在前面，
	 *     否则若 str 为 null 调用 trim()会出现空指针异常。
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		return (str==null || "".equals(str.trim()));
	}
	
	/**
	 * 字符串是否不为空
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String str) {
		return (str!=null && !"".equals(str.trim()));
	}
	
	/*public static void main(String[] args) {
		System.out.println(StringUtil.isEmpty(null));
		System.out.println(StringUtil.isEmpty(""));
		System.out.println(StringUtil.isEmpty("wo"));
	}*/
}
