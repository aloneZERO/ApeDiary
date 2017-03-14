package com.leo.apediary.test;


import com.leo.apediary.util.InitUtils;
import org.junit.Test;

/**
 * 配置工具类测试
 * @author leo
 */
public class ConfigUtilsTest {
	
	@Test
	public void testConfigUtils() {
		System.out.println(InitUtils.getProperty(""));
		System.out.println(InitUtils.getProperty("pageSize"));
	}
	
}
