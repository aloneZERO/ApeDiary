package com.leo.apediary.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.leo.apediary.dao.DiaryDAO;
import com.leo.apediary.domain.Category;
import com.leo.apediary.domain.Diary;
import com.leo.apediary.util.DBCPUtils;
import com.leo.apediary.web.bean.DiarySearchBean;
import com.mysql.jdbc.StringUtils;

public class DiaryDAOImpl implements DiaryDAO {
	
	private QueryRunner qr = new QueryRunner(DBCPUtils.getDataSource());
	
	@Override
	public int save(Diary diary) {
		String sql = "insert into diaries(id,title,content,categoryId,releaseDate) "
				+ "values(?,?,?,?,now())";
		try {
			return qr.update(sql, diary.getId(),diary.getTitle(),diary.getContent(),
					diary.getCategory().getId());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public int del(String diaryId) {
		String sql = "delete from diaries where id=?";
		try {
			return qr.update(sql, diaryId);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public int update(Diary diary) {
		String sql = "update diaries set title=?,content=?,categoryId=?,releaseDate=now() "
				+ "where id=?";
		try {
			return qr.update(sql, diary.getTitle(),diary.getContent(),
					diary.getCategory().getId(),diary.getId());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Diary findById(String diaryId) {
		String sql1 = "select * from diaries where id=?";
		String sql2 = "select * from categories where id=(select categoryId from diaries where id=?)";
		try {
			 Diary diary = qr.query(sql1, new BeanHandler<Diary>(Diary.class), diaryId);
			if(diary != null) {
				Category c = qr.query(sql2, new BeanHandler<Category>(Category.class),diaryId);
				diary.setCategory(c);
			}
			return diary;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public int getTotalRecordsNum(DiarySearchBean diarySearchBean) {
		StringBuffer sql = new StringBuffer("select count(*) from diaries "
				+ "where 1=1 ");
		if (diarySearchBean != null) {
			if (!StringUtils.isNullOrEmpty(diarySearchBean.getTitle())) {
				sql.append(" and title like '%"+diarySearchBean.getTitle()+"%' ");
			}
			if (diarySearchBean.getCategory()!=null &&
					!StringUtils.isNullOrEmpty(diarySearchBean.getCategory().getId())) {
				sql.append(" and categoryId='"+diarySearchBean.getCategory().getId()+"'");
			}
			if(!StringUtils.isNullOrEmpty(diarySearchBean.getReleaseDateStr())) {
				sql.append(" and DATE_FORMAT(releaseDate,'%Y年%m月')='"+
						diarySearchBean.getReleaseDateStr()+"'");
			}
		}
		try {
			Long ans = qr.query(sql.toString(), new ScalarHandler<Long>(1));
			return ans.intValue();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<DiarySearchBean> findPageRecords(int startIndex, int pageSize, DiarySearchBean diarySearchBean) {
		StringBuffer sql1 = new StringBuffer("select * from diaries "
				+ "where 1=1 ");
		if (diarySearchBean != null) {
			if (!StringUtils.isNullOrEmpty(diarySearchBean.getTitle())) {
				sql1.append(" and title like '%"+diarySearchBean.getTitle()+"%' ");
			}
			if (diarySearchBean.getCategory()!=null &&
					!StringUtils.isNullOrEmpty(diarySearchBean.getCategory().getId())) {
				sql1.append(" and categoryId='"+diarySearchBean.getCategory().getId()+"'");
			}
			if(!StringUtils.isNullOrEmpty(diarySearchBean.getReleaseDateStr())) {
				sql1.append(" and DATE_FORMAT(releaseDate,'%Y年%m月')='"+
						diarySearchBean.getReleaseDateStr()+"'");
			}
		}
		sql1.append(" order by releaseDate desc limit "+startIndex+", "+pageSize);
		String sql2 = "select * from categories where id=(select categoryId from diaries where id=?)";
		try {
			List<DiarySearchBean> beans = qr.query(sql1.toString(), new BeanListHandler<DiarySearchBean>(DiarySearchBean.class));
			if (beans!=null && beans.size()>0) {
				for (DiarySearchBean bean: beans) {
					Category c = qr.query(sql2, new BeanHandler<Category>(Category.class), bean.getId());
					bean.setCategory(c);
				}
			}
			return beans;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<DiarySearchBean> findInfoByReleaseDate() {
		String sql="select date_format(releaseDate,'%Y年%m月') "
				+ "as releaseDateStr,count(*) as diaryCount from diaries "
				+ "group by releaseDateStr order by releaseDateStr desc";
		try {
			return qr.query(sql, new BeanListHandler<DiarySearchBean>(DiarySearchBean.class));
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
