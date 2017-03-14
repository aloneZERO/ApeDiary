package com.leo.apediary.dao.impl;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.leo.apediary.dao.UserDAO;
import com.leo.apediary.domain.User;
import com.leo.apediary.util.DBCPUtils;

public class UserDAOImpl implements UserDAO {
	
	private QueryRunner qr = new QueryRunner(DBCPUtils.getDataSource());

	@Override
	public User find(String username, String password) {
		String sql = "select * from users where username=? and password=?";
		try {
			return qr.query(sql, new BeanHandler<User>(User.class), username,password);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public int update(User user) {
		String sql = "update users set nickname=?,path=?,imagename=?,description=? where id=?";
		try {
			return qr.update(sql, user.getNickname(),user.getPath(),
					user.getImagename(),user.getDescription(),user.getId());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
}
