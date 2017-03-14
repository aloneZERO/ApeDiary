package com.leo.apediary.web.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.leo.apediary.constant.Constants;
import com.leo.apediary.domain.Category;
import com.leo.apediary.domain.Diary;
import com.leo.apediary.service.BusinessService;
import com.leo.apediary.service.impl.BusinessServiceImpl;
import com.leo.apediary.util.WebUtils;
import com.mysql.jdbc.StringUtils;

/**
 * Diary Controller
 */
public class DiaryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BusinessService bs = new BusinessServiceImpl();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		if ("writeUI".equals(action)) {
			writeUI(request, response);
		} else if ("save".equals(action)) {
			save(request, response);
		} else if ("readUI".equals(action)) {
			readUI(request, response);
		} else if ("del".equals(action)) {
			 del(request, response);
		}
	}
	
	// 删除日记
	private void del(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String diaryId = request.getParameter("diaryId");
		int delNum = bs.delDiary(diaryId);
		if (delNum > 0) {
			response.sendRedirect(request.getContextPath()+"/client/home?re=zero");
		} else {
			request.setAttribute(Constants.ERROR_INFO, "删除失败");
			readUI(request, response);
		}
	}

	// 设置日记阅读 UI
	private void readUI(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String diaryId = request.getParameter("diaryId");
		if (!StringUtils.isNullOrEmpty(diaryId)) {
			Diary diary = bs.findDiaryById(diaryId);
			request.setAttribute("diary", diary);
			request.setAttribute(Constants.MAIN_PAGE, "diary/diaryShow.jsp");
			request.getRequestDispatcher("/home.jsp").forward(request, response);
		} else {
			request.setAttribute(Constants.ERROR_INFO, "日记君丢失~？？？");
			request.getRequestDispatcher("/home.jsp").forward(request, response);
		}
	}

	// 保存或更新日记
	private void save(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Diary diary = WebUtils.fillBean(request, Diary.class);
		String categoryId = request.getParameter("categoryId");
		if (!StringUtils.isNullOrEmpty(categoryId)) {
			diary.setCategory(bs.findCategoryById(categoryId));
		}
		
		int flag = 0;
		if (StringUtils.isNullOrEmpty(diary.getId())) {
			flag = bs.addDiary(diary);
		} else {
			flag = bs.updateDiary(diary);
		}
		if (flag > 0) {
			response.sendRedirect(request.getContextPath()+"/client/home?re=zero");
		} else {
			request.setAttribute("diary", diary);
			request.setAttribute(Constants.ERROR_INFO, "保存失败");
			request.setAttribute(Constants.MAIN_PAGE, "diary/diarySave.jsp");
			request.getRequestDispatcher("/home.jsp").forward(request, response);
		}
	}

	// 设置写日记 UI
	private void writeUI(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String diaryId = request.getParameter("diaryId");
		if (!StringUtils.isNullOrEmpty(diaryId)) {
			Diary diary = bs.findDiaryById(diaryId);
			request.setAttribute("diary", diary);
		}
		List<Category> categories = bs.findAllCategories();
		request.setAttribute("categories", categories);
		request.setAttribute(Constants.MAIN_PAGE, "diary/diarySave.jsp");
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
