package com.leo.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期工具类
 * @author leo
 *
 */
public class DateUtil {
	/**
	 * 格式化日期
	 * @param date
	 * @param format
	 * @return
	 */
	public static String formatDate(Date date,String format) {
		String result = "";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		if(date != null) {
			result = sdf.format(date);
		}
		return result;
	}
	
	/**
	 * 格式化字符串
	 * @param str
	 * @param format
	 * @return
	 * @throws ParseException
	 * @throws Exception
	 */
	public static Date formatString(String str,String format)throws ParseException {
		if(StringUtil.isEmpty(str)) {
			return null;
		}
		return new SimpleDateFormat(format).parse(str);
	}
}
