package com.leo.web;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.leo.dao.DiaryDao;
import com.leo.dao.DiaryTypeDao;
import com.leo.model.Diary;
import com.leo.model.PageBean;
import com.leo.util.DbUtil;
import com.leo.util.PropertiesUtil;
import com.leo.util.StringUtil;

public class MainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DiaryDao diaryDao = new DiaryDao();
	private DiaryTypeDao diaryTypeDao = new DiaryTypeDao();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		String back = request.getParameter("back");
		String s_typeId = request.getParameter("s_typeId");
		String s_releaseDateStr = request.getParameter("s_releaseDateStr");
		String s_title = request.getParameter("s_title");
		String all = request.getParameter("all");
		String page = request.getParameter("page");
		Diary diary = new Diary();
		
		if("true".equals(all)) {
			if(StringUtil.isNotEmpty(s_title)) {
				diary.setTitle(s_title);
			}
			session.removeAttribute("s_releaseDateStr");
			session.removeAttribute("s_typeId");
			session.setAttribute("s_title", s_title);
		}else {
			if(StringUtil.isNotEmpty(s_typeId)) {
				diary.setTypeId(Integer.parseInt(s_typeId));
				session.setAttribute("s_typeId", s_typeId);
				session.removeAttribute("s_releaseDateStr");
				session.removeAttribute("s_title");
			}
			if(StringUtil.isNotEmpty(s_releaseDateStr)) {
//				s_releaseDateStr = new String(s_releaseDateStr.getBytes("gbk"),"UTF-8");
				diary.setReleaseDateStr(s_releaseDateStr);
				session.setAttribute("s_releaseDateStr", s_releaseDateStr);
				session.removeAttribute("s_typeId");
				session.removeAttribute("s_title");
			}
			if(StringUtil.isEmpty(s_typeId)) {
				Object obj = session.getAttribute("s_typeId");
				if(obj!=null) {
					diary.setTypeId(Integer.parseInt((String)obj));
				}
			}
			if(StringUtil.isEmpty(s_releaseDateStr)) {
				Object obj = session.getAttribute("s_releaseDateStr");
				if(obj!=null) {
					diary.setReleaseDateStr((String)obj);
				}
			}
			if(StringUtil.isEmpty(s_title)) {
				Object obj = session.getAttribute("s_title");
				if(obj!=null) {
					diary.setTitle((String)obj);
				}
			}
			if("home".equals(back)) {
				session.removeAttribute("s_typeId");
				session.removeAttribute("s_releaseDateStr");
				session.removeAttribute("s_title");
				diary = new Diary();
			}
		}
		
		if(StringUtil.isEmpty(page)) {
			page = "1";
		}
		Connection conn = null;
		PageBean pageBean = new PageBean(Integer.parseInt(page),Integer.parseInt(PropertiesUtil.getValue("pageSize")));
		try {
			conn = DbUtil.getConnection();
			int total = diaryDao.diaryCount(conn,diary);
			String pagination = this.getPagination(total, pageBean.getPage(), pageBean.getPageSize());
			request.setAttribute("pagination", pagination);
			request.setAttribute("diaryList", diaryDao.diaryList(conn,pageBean,diary));
			session.setAttribute("diaryTypeCountList", diaryTypeDao.diaryTypeCountList(conn));
			session.setAttribute("diaryCountList", diaryDao.diaryCountList(conn));
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
	 * @return 格式化后的分页导航的 html 代码字符串
	 */
	private String getPagination(int totalNum,int currentPage,int pageSize) {
		int totalPage = (totalNum%pageSize==0)?(totalNum/pageSize):(totalNum/pageSize+1);
		StringBuilder pageCode = new StringBuilder();
		
		// 分页导航：首页和上一页
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
		
		// 分页导航：数字页码
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
		
		// 分页导航：下一页和尾页
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
