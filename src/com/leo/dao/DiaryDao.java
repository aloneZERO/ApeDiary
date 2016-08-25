package com.leo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.leo.model.Diary;
import com.leo.model.PageBean;
import com.leo.util.DateUtil;
import com.leo.util.StringUtil;

/**
 * 日记Dao类
 * @author leo
 *
 */
public class DiaryDao {
	
	/**
	 * 返回日记列表
	 * @param conn
	 * @param pageBean
	 * @return
	 * @throws SQLException 
	 * @throws ParseException 
	 */
	public List<Diary> diaryList(Connection conn, PageBean pageBean, Diary s_diary)throws SQLException, ParseException {
		List<Diary> diaryList = new LinkedList<>();
		StringBuilder sql = new StringBuilder("select * from t_diary as td,t_diaryType as tdt "
				+ "where td.typeId=tdt.diaryTypeId ");
		if(StringUtil.isNotEmpty(s_diary.getTitle())) {
			sql.append(" and td.title like '%"+s_diary.getTitle()+"%'");
		}
		if(s_diary.getTypeId()!=-1) {
			sql.append(" and td.typeId="+s_diary.getTypeId());
		}
		if(StringUtil.isNotEmpty(s_diary.getReleaseDateStr())) {
			sql.append(" and DATE_FORMAT(td.releaseDate,'%Y年%m月')='"+s_diary.getReleaseDateStr()+"'");
		}
		sql.append(" order by td.releaseDate DESC");
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
			diary.setReleaseDate(DateUtil.formatString(rs.getString("releaseDate"),"yyyy-MM-dd HH:mm:ss") );
			diaryList.add(diary);
		}
		return diaryList;
	}
	
	/**
	 * 返回日记总数
	 * @param con
	 * @return
	 * @throws SQLException 
	 */
	public int diaryCount(Connection con, Diary s_diary)throws SQLException {
		StringBuilder sql = new StringBuilder("select count(*) as total from t_diary as td,t_diaryType as tdt "
				+ "where td.typeId=tdt.diaryTypeId ");
		if(StringUtil.isNotEmpty(s_diary.getTitle())) {
			sql.append(" and td.title like '%"+s_diary.getTitle()+"%'");
		}
		if(s_diary.getTypeId()!=-1) {
			sql.append(" and td.typeId="+s_diary.getTypeId());
		}
		if(StringUtil.isNotEmpty(s_diary.getReleaseDateStr())) {
			sql.append(" and DATE_FORMAT(td.releaseDate,'%Y年%m月')='"+s_diary.getReleaseDateStr()+"'");
		}
		PreparedStatement pstmt = con.prepareStatement(sql.toString());
		ResultSet rs = pstmt.executeQuery();
		if(rs.next()) {
			return rs.getInt("total");
		}else {
			return 0;
		}
	}
	
	/**
	 * 按日期返回日记数量表
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	public List<Diary> diaryCountList(Connection con) throws SQLException {
		List<Diary> diaryCountList = new ArrayList<Diary>();
		String sql="SELECT DATE_FORMAT(releaseDate,'%Y年%m月') as releaseDateStr ,COUNT(*) AS diaryCount  FROM t_diary "
				+"GROUP BY releaseDateStr ORDER BY releaseDateStr DESC";
		PreparedStatement pstmt = con.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()) {
			Diary diary = new Diary();
			diary.setReleaseDateStr(rs.getString("releaseDateStr"));
			diary.setDiaryCount(rs.getInt("diaryCount"));
			diaryCountList.add(diary);
		}
		return diaryCountList;
	}
	
	/**
	 * 返回当前需要显示的日记实体
	 * @param conn
	 * @param diaryId
	 * @return 查询的日记实体
	 * @throws SQLException
	 * @throws ParseException
	 */
	public Diary diaryShow(Connection conn,int diaryId) throws SQLException, ParseException {
		String sql = "select * from t_diary as td,t_diaryType as tdt "
				+ "where td.typeId=tdt.diaryTypeId "
				+ "and td.diaryId=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, diaryId);
		ResultSet rs = pstmt.executeQuery();
		Diary currentDiary = new Diary();
		while(rs.next()) {
			currentDiary.setDiaryId(rs.getInt("diaryId"));
			currentDiary.setTitle(rs.getString("title"));
			currentDiary.setContent(rs.getString("content"));
			currentDiary.setTypeId(rs.getInt("typeId"));
			currentDiary.setTypeName(rs.getString("typeName"));
			currentDiary.setReleaseDate(DateUtil.formatString(rs.getString("releaseDate"),"yyyy-MM-dd HH:mm:ss"));
		}
		return currentDiary;
	}
	
	/**
	 * 添加日记
	 * @param conn
	 * @param diary
	 * @return 添加日记成功的条数
	 * @throws SQLException
	 */
	public int diaryAdd(Connection conn, Diary diary) throws SQLException {
		String sql = "insert into t_diary values(null,?,?,?,now())";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, diary.getTitle());
		pstmt.setString(2, diary.getContent());
		pstmt.setInt(3, diary.getTypeId());
		return pstmt.executeUpdate();
	}
	
	/**
	 * 更新日记
	 * @param conn
	 * @param newDiary
	 * @return 更新日记成功的条数
	 * @throws SQLException
	 */
	public int diaryUpdate(Connection conn,Diary newDiary) throws SQLException {
		String sql = "update t_diary set title=?,content=?,typeId=? where diaryId=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, newDiary.getTitle());
		pstmt.setString(2, newDiary.getContent());
		pstmt.setInt(3, newDiary.getTypeId());
		pstmt.setInt(4, newDiary.getDiaryId());
		return pstmt.executeUpdate();
	}
	
	/**
	 * 删除日记
	 * @param conn
	 * @param diary
	 * @return 删除日记成功的条数
	 * @throws SQLException
	 */
	public int diaryDel(Connection conn,int diaryId) throws SQLException {
		String sql = "delete from t_diary where diaryId=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, diaryId);
		return pstmt.executeUpdate();
	}
	
	/**
	 * 判断指定日记类别下是否存在日记
	 * @param conn
	 * @param typeId
	 * @return 存在日记返回 true, 否则返回 false
	 * @throws SQLException
	 */
	public boolean existDiaryWithTypeId(Connection conn,int typeId) throws SQLException {
		String sql = "select * from t_diary where typeId=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, typeId);
		ResultSet rs = pstmt.executeQuery();
		if(rs.next()) return true;
		else          return false;
	}
}
