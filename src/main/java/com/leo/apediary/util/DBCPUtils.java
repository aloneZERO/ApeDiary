package com.leo.apediary.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSourceFactory;

public class DBCPUtils {
	private static DataSource dataSource;
	
	// 读取配置文件，初始化数据源
	static {
		try {
			Properties props = new Properties();
			InputStream in = DBCPUtils.class.getClassLoader()
					.getResourceAsStream("dbcpconfig.properties");
			props.load(in);
			dataSource = BasicDataSourceFactory.createDataSource(props);
		} catch (Exception e) {
			throw new ExceptionInInitializerError(e);
		}
	}
	
	/**
	 * 获取数据源
	 * @return 数据源实例
	 */
	public static DataSource getDataSource() {
		return dataSource;
	}
	
	/**
	 * 获取数据库连接
	 * @return 数据库连接
	 */
	public static Connection getConnection() {
		try {
			return dataSource.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
