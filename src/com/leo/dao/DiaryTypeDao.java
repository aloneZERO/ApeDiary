package com.leo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
	 * 返回日记类别日记数量表
	 * @param conn
	 * @param diaryType
	 * @return
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
}
