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
		System.out.println("数据源测试 ===========================");
		DataSource dataSource = DBCPUtils.getDataSource();
		Assert.assertNotNull(dataSource);
		System.out.println(dataSource.getClass());
		System.out.println("=====================================\n");
	}
	
	/*
	 * 测试数据库连接
	 */
	@Test
	public void testConnect() {
		System.out.println("数据库连接测试 =======================");
		Connection conn = DBCPUtils.getConnection();
		Assert.assertNotNull(conn);
		System.out.println(conn.getClass());
		System.out.println("====================================\n");
	}
	
}
