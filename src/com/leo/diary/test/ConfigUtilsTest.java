package com.leo.diary.test;

import org.junit.Test;

import com.leo.diary.util.InitUtils;

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
