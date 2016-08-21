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
		}
	}
	
	private void diaryShow(HttpServletRequest request, HttpServletResponse response) {
		String diaryId = request.getParameter("diaryId");
		Connection conn = null;
		try {
			conn = DbUtil.getConnection();
			Diary diary = diaryDao.diaryShow(conn, Integer.parseInt(diaryId));
			request.setAttribute("diary", diary);
			request.setAttribute("mainPage", "diary/diaryShow.jsp");
			request.getRequestDispatcher("main.jsp").forward(request, response);
		} catch (ClassNotFoundException | SQLException | ParseException
				| ServletException | IOException e) {
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
