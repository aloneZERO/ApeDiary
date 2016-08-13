package com.leo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;

import com.leo.model.Diary;
import com.leo.model.PageBean;
import com.leo.util.DateUtil;

/**
 * 日记Dao类
 * @author leo
 *
 */
public class DiaryDao {
	
	/**
	 * 获得日记列表
	 * @param conn
	 * @param pageBean
	 * @return
	 * @throws SQLException 
	 * @throws ParseException 
	 */
	public List<Diary> diaryList(Connection conn, PageBean pageBean)throws SQLException, ParseException {
		List<Diary> diaryList = new LinkedList<>();
		StringBuilder sql = new StringBuilder("select * from t_diary as td,t_diaryType as tdt "
				+ "where td.typeId=tdt.diaryTypeId ");
		sql.append("order by td.releaseDate desc");
		if(pageBean != null) {
			sql.append(" limit "+pageBean.getStart()+","+pageBean.getPageSize());
		}
		PreparedStatement pstmt = conn.prepareStatement(sql.toString());
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()) {
			Diary diary=new Diary();
			diary.setDiaryId(rs.getInt("diaryId"));
			diary.setTitle(rs.getString("title"));
			diary.setContent(rs.getString("content"));
			diary.setReleaseDate(DateUtil.formatString(rs.getString("releaseDate"), "yyyy-MM-dd HH:mm:ss"));
			diaryList.add(diary);
		}
		return diaryList;
	}
	
	/**
	 * 获取日记总数
	 * @param con
	 * @return
	 * @throws SQLException 
	 */
	public int diaryCount(Connection con)throws SQLException {
		StringBuffer sql = new StringBuffer("select count(*) as total from t_diary as td,t_diaryType as tdt "
				+ "where td.typeId=tdt.diaryTypeId ");
		PreparedStatement pstmt = con.prepareStatement(sql.toString());
		ResultSet rs = pstmt.executeQuery();
		if(rs.next()) {
			return rs.getInt("total");
		}else {
			return 0;
		}
	}
	
}
