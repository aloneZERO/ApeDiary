package com.leo.apediary.test;


import java.sql.Connection;

import javax.sql.DataSource;

import com.leo.apediary.util.DBCPUtils;
import org.junit.Assert;
import org.junit.Test;


public class DBCPUtilsTest {
	
	/*
	 * 测试数据源
	 */
	@Test
	public void testDataSource() {
		DataSource dataSource = DBCPUtils.getDataSource();
		Assert.assertNotNull(dataSource);
		System.out.println(dataSource.getClass());
	}
	
	/*
	 * 测试数据库连接
	 */
	@Test
	public void testConnect() {
		Connection conn = DBCPUtils.getConnection();
		Assert.assertNotNull(conn);
		System.out.println(conn.getClass());
	}
	
}
