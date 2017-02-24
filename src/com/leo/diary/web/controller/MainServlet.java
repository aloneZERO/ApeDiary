package com.leo.diary.web.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.leo.diary.common.Page;
import com.leo.diary.constant.Constants;
import com.leo.diary.service.BusinessService;
import com.leo.diary.service.impl.BusinessServiceImpl;
import com.leo.diary.util.WebUtils;
import com.leo.diary.web.bean.DiarySearchBean;
import com.mysql.jdbc.StringUtils;

/**
 * 首页控制器
 */
public class MainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BusinessService bs = new BusinessServiceImpl();
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		DiarySearchBean sInfo = initSearchInfo(request, response);
		homeUI(request, response, sInfo);
	}
	
	// 初始化搜索信息-首页搜索功能
	private DiarySearchBean initSearchInfo(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		DiarySearchBean sInfo = new DiarySearchBean(); // 规避空指针
		sInfo = WebUtils.fillBean(request, DiarySearchBean.class);
		String categoryId = request.getParameter("categoryId");
		if (!StringUtils.isNullOrEmpty(categoryId)) {
			sInfo.setCategory(bs.findCategoryById(categoryId));
		}
		
		String re = request.getParameter("re");
		HttpSession session = request.getSession();
		if ("zero".equals(re)) {
			session.removeAttribute("s_categoryId");
			session.removeAttribute("s_releaseDateStr");
			session.removeAttribute("s_title");
			return null;
		}
		
		if (!StringUtils.isNullOrEmpty(sInfo.getTitle())) {
			session.setAttribute("s_title", sInfo.getTitle());
			session.removeAttribute("s_releaseDate");
			session.removeAttribute("s_categoryId");
		} else {
			if (!StringUtils.isNullOrEmpty(categoryId)) {
				session.setAttribute("s_categoryId", categoryId);
				session.removeAttribute("s_releaseDateStr");
				session.removeAttribute("s_title");
			}
			if (!StringUtils.isNullOrEmpty(sInfo.getReleaseDateStr())) {
				session.setAttribute("s_releaseDateStr", sInfo.getReleaseDateStr());
				session.removeAttribute("s_categoryId");
				session.removeAttribute("s_title");
			}
			
			if (StringUtils.isNullOrEmpty(categoryId)) {
				String s = (String) session.getAttribute("s_categoryId");
				if (!StringUtils.isNullOrEmpty(s)) {
					sInfo.setCategory(bs.findCategoryById(s));
				}
			}
			if (StringUtils.isNullOrEmpty(sInfo.getReleaseDateStr())) {
				String s = (String) session.getAttribute("s_releaseDateStr");
				if (!StringUtils.isNullOrEmpty(s)) {
					sInfo.setReleaseDateStr(s);
				}
			}
			if (StringUtils.isNullOrEmpty(sInfo.getTitle())) {
				String s = (String) session.getAttribute("s_title");
				if (!StringUtils.isNullOrEmpty(s)) {
					sInfo.setTitle(s);
				}
			}
		}
		return sInfo;
	}

	// 设置首页 UI
	private void homeUI(HttpServletRequest request, HttpServletResponse response,
			DiarySearchBean sInfo) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String num = request.getParameter("num");
		Page page = bs.findDiaryPageRecords(num, sInfo);
		page.setUrl("/client/home");
		request.setAttribute("page", page);
		session.setAttribute("categoryInfoList", bs.findDiayInfoByCategory());
		session.setAttribute("releaseDateInfoList", bs.findDiaryInfoByReleaseDate());
		request.setAttribute(Constants.MAIN_PAGE, "diary/diaryList.jsp");
		request.getRequestDispatcher("/home.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
