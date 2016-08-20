package com.leo.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {
	private PropertiesUtil() {}
	
	/**
	 * 根据键获取配置文件中的键值
	 * @param key
	 * @return 键值字符串
	 */
	public static String getValue(String key) {
		Properties prop = new Properties();
		InputStream in = new PropertiesUtil().getClass().getResourceAsStream("/diary.properties");
		try {
			prop.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop.getProperty(key);
	}
}
