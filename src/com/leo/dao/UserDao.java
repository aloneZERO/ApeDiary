package com.leo.dao;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.leo.model.User;
import com.leo.util.Md5Util;

public class UserDao {
	
	/**
	 * 用户登录验证
	 * @param conn
	 * @param user
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException 
	 * @throws NoSuchAlgorithmException 
	 */
	public User login(Connection conn,User user) throws SQLException, NoSuchAlgorithmException, UnsupportedEncodingException {
		User resultUser = null;
		String sql = "select * from t_user where userName=? and password=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, user.getUserName());
		pstmt.setString(2, Md5Util.encrypteByMd5(user.getPassword()));
		ResultSet rs = pstmt.executeQuery();
		if(rs.next()) {
			resultUser = new User();
			resultUser.setUserId(rs.getInt("userId"));
			resultUser.setUserName(rs.getString("userName"));
			resultUser.setPassword(rs.getString("password"));
			/*resultUser.setNickName(rs.getString("nickName"));
			resultUser.setIconName(rs.getString("iconName"));
			resultUser.setMood(rs.getString("mood"));*/
		}
		return resultUser;
	}
}
