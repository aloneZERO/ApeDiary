package com.leo.web;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.leo.dao.DiaryDao;
import com.leo.model.Diary;
import com.leo.util.DbUtil;
import com.leo.util.StringUtil;

public class DiaryServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	DiaryDao diaryDao = new DiaryDao();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		if("show".equals(action)) {
			diaryShow(request, response);
		}else if("preSave".equals(action)) {
			diaryPreSave(request, response);
		}else if("save".equals(action)) {
			diarySave(request, response);
		}else if("delete".equals(action)){
			diaryDel(request,response);
		}
	}
	
	private void diaryShow(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String diaryId = request.getParameter("diaryId");
		Connection conn = null;
		try {
			conn = DbUtil.getConnection();
			Diary diary = diaryDao.diaryShow(conn, Integer.parseInt(diaryId));
			request.setAttribute("diary", diary);
			request.setAttribute("mainPage", "diary/diaryShow.jsp");
			request.getRequestDispatcher("main.jsp").forward(request, response);
		} catch (ClassNotFoundException | SQLException | ParseException e) {
			e.printStackTrace();
		}finally {
			try {
				DbUtil.close(conn);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void diarySave(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String diaryId = request.getParameter("diaryId");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String typeId = request.getParameter("typeId");
		Diary diary = new Diary(title,content,Integer.parseInt(typeId));
		if(StringUtil.isNotEmpty(diaryId)) {
			diary.setDiaryId(Integer.parseInt(diaryId));
		}
		
		Connection conn = null;
		try {
			conn = DbUtil.getConnection();
			int saveNum = 0;
			if(StringUtil.isNotEmpty(diaryId)) {
				saveNum = diaryDao.diaryUpdate(conn, diary);	
			}else {
				saveNum = diaryDao.diaryAdd(conn, diary);				
			}
			if(saveNum > 0) {
				request.getRequestDispatcher("main?all=true").forward(request, response);
			}else {
				request.setAttribute("diary", diary);
				request.setAttribute("error", "保存失败");
				request.setAttribute("mainPage", "diary/diarySave.jsp");
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
	private void diaryPreSave(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String diaryId = request.getParameter("diaryId");
		Connection conn = null;
		try {
			conn = DbUtil.getConnection();
			if(StringUtil.isNotEmpty(diaryId)) {
				Diary diary = diaryDao.diaryShow(conn, Integer.parseInt(diaryId));
				request.setAttribute("diary", diary);
			}
			request.setAttribute("mainPage", "diary/diarySave.jsp");
			request.getRequestDispatcher("main.jsp").forward(request, response);
		} catch (ClassNotFoundException | SQLException |
				NumberFormatException | ParseException e) {
			e.printStackTrace();
		}
	}
	
	private void diaryDel(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String diaryId = request.getParameter("diaryId");
		Connection conn = null;
		try {
			conn = DbUtil.getConnection();
			int delNum = diaryDao.diaryDel(conn, Integer.parseInt(diaryId));
			if(delNum >0) {
				request.getRequestDispatcher("main?all=true").forward(request, response);
			}else {
				request.setAttribute("error", "删除失败");
				diaryShow(request, response);
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
	
}
