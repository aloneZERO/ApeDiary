package com.leo.web;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.leo.dao.DiaryDao;
import com.leo.model.Diary;
import com.leo.model.PageBean;
import com.leo.util.DbUtil;
import com.leo.util.PropertiesUtil;
import com.leo.util.StringUtil;

public class MainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
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
		String page = request.getParameter("page");
		if(StringUtil.isEmpty(page)) {
			page = "1";
		}
		Connection conn = null;
		PageBean pageBean = new PageBean(Integer.parseInt(page),Integer.parseInt(PropertiesUtil.getValue("pageSize")));
		try {
			conn = DbUtil.getConnection();
			List<Diary> diaryList = diaryDao.diaryList(conn,pageBean);
			int total = diaryDao.diaryCount(conn);
			String pageCode = this.getPagination(total, pageBean.getPage(), pageBean.getPageSize());
			request.setAttribute("pageCode", pageCode);
			request.setAttribute("diaryList", diaryList);
			request.setAttribute("mainPage", "diary/diaryList.jsp");
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
	
	/**
	 * 获取分页导航
	 * @param totalNum
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	private String getPagination(int totalNum,int currentPage,int pageSize) {
		int totalPage = (totalNum%pageSize==0)?(totalNum/pageSize):(totalNum/pageSize+1);
		StringBuilder pageCode = new StringBuilder();
		if(currentPage==1) {
			pageCode.append("<li class='disabled'><a href='#'>首页</a></li>");
		}else {
			pageCode.append("<li><a href='main?page=1'>首页</a></li>");
		}
		if(currentPage==1) {
			pageCode.append("<li class='disabled'><a href='#'>上一页</a></li>");
		}else {
			pageCode.append("<li><a href='main?page="+(currentPage-1)+"'>上一页</a></li>");
		}
		for(int i=currentPage-2; i<= currentPage+2; i++) {
			if(i<1||i>totalPage) {
				continue;
			}
			if(i==currentPage) {
				pageCode.append("<li class='active'><a href='#'>"+i+"</a></li>");
			}else {
				pageCode.append("<li><a href='main?page="+i+"'>"+i+"</a></li>");
			}
		}
		if(currentPage==totalPage) {
			pageCode.append("<li class='disabled'><a href='#'>下一页</a></li>");
		}else {
			pageCode.append("<li><a href='main?page="+(currentPage+1)+"'>下一页</a></li>");
		}
		if(currentPage==totalPage) {
			pageCode.append("<li class='disabled'><a href='#'>尾页</a></li>");
		}else {
			pageCode.append("<li><a href='main?page="+totalPage+"'>尾页</a></li>");
		}
		return pageCode.toString();
	}
	
}
