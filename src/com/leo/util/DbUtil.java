package com.leo.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 数据库工具类
 * @author leo
 *
 */
public class DbUtil {
	
	/**
	 * 构造私有化
	 */
	private DbUtil() {
		// do nothing...
	}
	
	/**
	 * 获取数据库连接
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName(PropertiesUtil.getValue("jdbcName"));
		return DriverManager.getConnection(PropertiesUtil.getValue("dbUrl"),
				PropertiesUtil.getValue("dbUsername"),PropertiesUtil.getValue("dbPassword"));
	}
	
	/**
	 * 关闭数据库连接
	 * @param connection
	 * @throws SQLException
	 */
	public static void close(Connection conn) throws SQLException {
		if(conn!=null) {
			conn.close();
		}
	}
	
	/**
	 * 数据库工具类测试方法
	 * @param args
	 */
	public static void main(String[] args) {
//		System.out.println(PropertiesUtil.getValue("dbUrl"));
		Connection conn = null;
		try {
			conn = DbUtil.getConnection();
			if(conn!=null) {
				System.out.println("数据连接成功");
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			System.out.println("数据库连接失败");
		}finally {
			try {
				DbUtil.close(conn);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
}
