package com.leo.web;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.leo.dao.UserDao;
import com.leo.model.User;
import com.leo.util.DbUtil;

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDao userDao = new UserDao();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		String remember = request.getParameter("remember");
		Connection conn = null;
		try {
			conn = DbUtil.getConnection();
			User user = new User(userName,password);
			User currentUser = userDao.login(conn, user);
			if(currentUser == null) {
				if("remember-me".equals(remember)) {
					rememberMe(userName,response);
				}
				request.setAttribute("user", user);
				request.setAttribute("error", "用户名或密码错误");
				request.getRequestDispatcher("login.jsp").forward(request, response);
			}else {
//				System.out.println("success");
				if("remember-me".equals(remember)) {
					rememberMe(userName,password,response);
				}
				session.setAttribute("currentUser", currentUser);
				response.sendRedirect("main.jsp");
			}
		}catch (ClassNotFoundException | SQLException | NoSuchAlgorithmException e) {
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
	 * checkbox remember-me, set cookie
	 * @param userName
	 * @param password
	 * @param response
	 */
	private void rememberMe(String userName, String password, HttpServletResponse response) {
		Cookie user = new Cookie("user", userName+"-"+password);
		user.setMaxAge(1*60*60*24*7); //cookie时间设为一星期
		response.addCookie(user);
	}
	private void rememberMe(String userName, HttpServletResponse response) {
		String password = null;
		Cookie user = new Cookie("user", userName+"-"+password);
		user.setMaxAge(1*60*60*24*7); //cookie时间设为一星期
		response.addCookie(user);
	}
}
