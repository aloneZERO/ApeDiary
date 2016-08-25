package com.leo.web;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.leo.dao.DiaryDao;
import com.leo.dao.DiaryTypeDao;
import com.leo.model.DiaryType;
import com.leo.util.DbUtil;
import com.leo.util.StringUtil;

public class DiaryTypeServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private DiaryTypeDao diaryTypeDao = new DiaryTypeDao();
	private DiaryDao diaryDao = new DiaryDao();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String action = request.getParameter("action");
		if("list".equals(action)) {
			diaryTypeList(request,response);
		}else if("preSave".equals(action)) {
			diaryTypePreSave(request,response);
		}else if("save".equals(action)) {
			diaryTypeSave(request,response);
		}else if("delete".equals(action)){
			 diaryTypeDel(request,response);
		}
	}
	
	private void diaryTypeList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection conn = null;
		try {
			conn = DbUtil.getConnection();
			List<DiaryType> diaryTypeList = diaryTypeDao.diaryTypeList(conn);
			request.setAttribute("diaryTypeList", diaryTypeList);
			request.setAttribute("mainPage", "diaryType/diaryTypeList.jsp");
			request.getRequestDispatcher("main.jsp").forward(request, response);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				DbUtil.close(conn);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	private void diaryTypePreSave(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String diaryTypeId = request.getParameter("diaryTypeId");
		if(StringUtil.isNotEmpty(diaryTypeId)) {
			Connection conn = null;
			try {
				conn = DbUtil.getConnection();
				DiaryType diaryType = diaryTypeDao.diaryTypeShow(conn, Integer.parseInt(diaryTypeId));
				request.setAttribute("diaryType", diaryType);
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}finally {
				try {
					DbUtil.close(conn);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		request.setAttribute("mainPage", "diaryType/diaryTypeSave.jsp");
		request.getRequestDispatcher("main.jsp").forward(request, response);
	}
	
	private void diaryTypeSave(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		String diaryTypeId = request.getParameter("diaryTypeId");
		String typeName = request.getParameter("typeName");
		DiaryType diaryType = new DiaryType(typeName);
		if(StringUtil.isNotEmpty(diaryTypeId)){
			diaryType.setDiaryTypeId(Integer.parseInt(diaryTypeId));
		}
		
		Connection conn = null;
		try {
			conn = DbUtil.getConnection();
			int saveNum = 0;
			if(StringUtil.isNotEmpty(diaryTypeId)) {
				saveNum = diaryTypeDao.diaryTypeUpdate(conn, diaryType);
			}else{
				saveNum = diaryTypeDao.diaryTypeAdd(conn, diaryType);
			}
			if(saveNum > 0) {
				session.setAttribute("diaryTypeCountList", diaryTypeDao.diaryTypeCountList(conn));
				request.getRequestDispatcher("diaryType?action=list").forward(request, response);
			}else{
				request.setAttribute("diaryType", diaryType);
				request.setAttribute("error", "保存失败！");
				request.setAttribute("mainPage", "diaryType/diaryTypeSave.jsp");
				request.getRequestDispatcher("main.jsp").forward(request, response);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				DbUtil.close(conn);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void diaryTypeDel(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String diaryTypeId = request.getParameter("diaryTypeId");
		Connection conn = null;
		try {
			conn = DbUtil.getConnection();
			if(diaryDao.existDiaryWithTypeId(conn, Integer.parseInt(diaryTypeId))) {
				request.setAttribute("error","此日记类别下存在日记，无法删除！");
			}else {
				int delNum = diaryTypeDao.diaryTypeDel(conn, Integer.parseInt(diaryTypeId));
				if(delNum<=0) request.setAttribute("error","删除失败");
			}
			request.setAttribute("diaryTypeCountList", diaryTypeDao.diaryTypeCountList(conn));
			request.getRequestDispatcher("diaryType?action=list").forward(request, response);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				DbUtil.close(conn);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
}
