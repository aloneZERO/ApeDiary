package com.leo.apediary.web.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.leo.apediary.constant.Constants;
import com.leo.apediary.domain.Category;
import com.leo.apediary.service.BusinessService;
import com.leo.apediary.service.impl.BusinessServiceImpl;
import com.leo.apediary.util.WebUtils;
import com.mysql.jdbc.StringUtils;

/**
 * Category Controller
 */
public class CategoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BusinessService bs = new BusinessServiceImpl();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		
		if("list".equals(action)) {
			categoryList(request, response);
		} else if("manageUI".equals(action)) {
			manageUI(request, response);
		} else if("save".equals(action)) {
			 saveCategory(request, response);
		} else if ("del".equals(action)) {
			delCategory(request, response);
		}
	}
	
	// 删除一个分类
	private void delCategory(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String categoryId = request.getParameter("categoryId");
		if(bs.existDiary(categoryId)) {
			request.setAttribute(Constants.ERROR_INFO,"类别下存在日记，无法删除！");
		} else {
			int delNum = bs.delCategory(categoryId);
			if(delNum <= 0) request.setAttribute(Constants.ERROR_INFO,"删除失败");
		}
		request.getRequestDispatcher("/client/category?action=list").forward(request, response);
	}

	// 添加或更新一个分类
	private void saveCategory(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Category category = WebUtils.fillBean(request, Category.class);
		int flag;
		if(StringUtils.isNullOrEmpty(category.getId())) {
			flag = bs.addCategory(category);
		} else {
			flag = bs.updateCategory(category);
		}
		
		if(flag > 0) {
			request.getRequestDispatcher("/client/category?action=list").forward(request, response);
		} else {
			request.setAttribute("category", category);
			request.setAttribute(Constants.ERROR_INFO, "保存失败！");
			request.setAttribute(Constants.MAIN_PAGE, "category/categorySave.jsp");
			request.getRequestDispatcher("/home.jsp").forward(request, response);
		}
	}

	// 设置管理分类 UI
	private void manageUI(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String categoryId = request.getParameter("categoryId");
		if(!StringUtils.isNullOrEmpty(categoryId)) {
			Category category = bs.findCategoryById(categoryId);
			request.setAttribute("category", category);
		}
		request.setAttribute(Constants.MAIN_PAGE, "category/categorySave.jsp");
		request.getRequestDispatcher("/home.jsp").forward(request, response);
	}

	// 设置分类列表
	private void categoryList(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		List<Category> categoryList = bs.findAllCategories();
		request.setAttribute("categoryList", categoryList);
		request.setAttribute(Constants.MAIN_PAGE, "category/categoryList.jsp");
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
