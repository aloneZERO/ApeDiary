package com.leo.apediary.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.leo.apediary.dao.CategoryDAO;
import com.leo.apediary.domain.Category;
import com.leo.apediary.util.DBCPUtils;
import com.leo.apediary.web.bean.CategoryInfoBean;

public class CategoryDAOImpl implements CategoryDAO {
	
	private QueryRunner qr = new QueryRunner(DBCPUtils.getDataSource());
	
	@Override
	public int save(Category c) {
		String sql = "insert into categories (id,name) values (?,?)";
		try {
			return qr.update(sql, c.getId(),c.getName());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<Category> findAll() {
		String sql = "select * from categories";
		try {
			return qr.query(sql, new BeanListHandler<Category>(Category.class));
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Category findById(String categoryId) {
		String sql = "select * from categories where id=?";
		try {
			return qr.query(sql, new BeanHandler<Category>(Category.class), categoryId);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public int del(String categoryId) {
		String sql = "delete from categories where id=?";
		try {
			return qr.update(sql, categoryId);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public int diaryCount(String categoryId) {
		String sql = "select count(*) from diaries where categoryId=?";
		try {
			Long res = qr.query(sql, new ScalarHandler<Long>(1), categoryId);
			return res.intValue();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public int update(Category c) {
		String sql = "update categories set name=? where id=?";
		try {
			return qr.update(sql, c.getName(),c.getId());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public int getTotalRecordsNum() {
		String sql = "select count(*) from categories";
		try {
			Long res = qr.query(sql, new ScalarHandler<Long>(1));
			return res.intValue();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<CategoryInfoBean> findDiaryInfo() {
		String sql = "select c.id,c.name,count(d.id) diaryCount from diaries d "
				+ "right outer join categories c "
				+ "on d.categoryId=c.id "
				+ "group by c.id,c.name";
		try {
			return qr.query(sql, new BeanListHandler<CategoryInfoBean>(CategoryInfoBean.class));
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
}
