package com.leo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.LinkedList;
import java.util.List;

import com.leo.model.DiaryType;

/**
 * 日记类别Dao类
 * @author leo
 *
 */
public class DiaryTypeDao {
	
	/**
	 * 返回日记类别信息表：类别信息和类别对应下的日记总数
	 * @param conn
	 * @param diaryType
	 * @return 日记类别信息表：类别信息和类别对应下的日记总数
	 * @throws SQLException 
	 */
	public List<DiaryType> diaryTypeCountList(Connection conn) throws SQLException {
		List<DiaryType> diaryTypeCountList = new LinkedList<>();
		String sql = "SELECT tdt.diaryTypeId,tdt.typeName,COUNT(td.diaryId) AS diaryCount " 
				+"FROM t_diary AS td RIGHT JOIN t_diaryType AS tdt ON td.typeId=tdt.diaryTypeId "
				+"GROUP BY tdt.diaryTypeId,tdt.typeName";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()) {
			DiaryType diaryType = new DiaryType();
			diaryType.setDiaryTypeId(rs.getInt("diaryTypeId"));
			diaryType.setTypeName(rs.getString("typeName"));
			diaryType.setDiaryCount(rs.getInt("diaryCount"));
			diaryTypeCountList.add(diaryType);
		}
		return diaryTypeCountList;
	}
	
	/**
	 * 返回日记类别信息表
	 * @param con
	 * @return 日记类别信息表
	 * @throws SQLException 
	 * @throws Exception
	 */
	public List<DiaryType> diaryTypeList(Connection conn) throws SQLException {
		List<DiaryType> diaryTypeList = new LinkedList<DiaryType>();
		String sql = "select * from t_diaryType";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()) {
			DiaryType diaryType = new DiaryType();
			diaryType.setDiaryTypeId(rs.getInt("diaryTypeId"));
			diaryType.setTypeName(rs.getString("typeName"));
			diaryTypeList.add(diaryType);
		}
		return diaryTypeList;
	}
	
	/**
	 * 添加日记类别
	 * @param conn
	 * @param diaryType
	 * @return 日记类别成功添加的条数
	 * @throws SQLException
	 */
	public int diaryTypeAdd(Connection conn,DiaryType diaryType) throws SQLException {
		String sql = "insert into t_diaryType values(?,?)";
		ResultSet rs = conn.createStatement().executeQuery("select max(diaryTypeId) as lastTypeId from t_diaryType");
		PreparedStatement pstmt = conn.prepareStatement(sql);
		if(rs.next()) {
			pstmt.setInt(1, rs.getInt("lastTypeId")+1);
		}else {
			pstmt.setNull(1,Types.INTEGER);
		}
		pstmt.setString(2, diaryType.getTypeName());
		return pstmt.executeUpdate();
	}
	
	/**
	 * 更新日记类别
	 * @param conn
	 * @param newDiaryType
	 * @return 日记类别更细成功条数
	 * @throws SQLException
	 */
	public int diaryTypeUpdate(Connection conn,DiaryType newDiaryType) throws SQLException {
		String sql = "update t_diaryType set typeName=? where diaryTypeId=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, newDiaryType.getTypeName());
		pstmt.setInt(2, newDiaryType.getDiaryTypeId());
		return pstmt.executeUpdate();
	}
	
	/**
	 * 返回当前需要显示日记类别实体
	 * @param conn
	 * @param diaryTypeId
	 * @return 查询的日记类别实体
	 * @throws SQLException
	 */
	public DiaryType diaryTypeShow(Connection conn,int diaryTypeId) throws SQLException {
		String sql="SELECT * from t_diaryType where diaryTypeId=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, diaryTypeId);
		ResultSet rs = pstmt.executeQuery();
		DiaryType diaryType = new DiaryType();
		if(rs.next()) {
			diaryType.setDiaryTypeId(rs.getInt("diaryTypeId"));
			diaryType.setTypeName(rs.getString("typeName"));
		}
		return diaryType;
	}
	
	/**
	 * 删除日记类别
	 * @param conn
	 * @param diaryTypeId
	 * @return 日记类别删除成功条数
	 * @throws SQLException
	 */
	public int diaryTypeDel(Connection conn,int diaryTypeId) throws SQLException {
		String sql = "delete from t_diaryType where diaryTypeId=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, diaryTypeId);
		return pstmt.executeUpdate();
	}
}
