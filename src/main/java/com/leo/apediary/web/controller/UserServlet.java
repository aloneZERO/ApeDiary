package com.leo.apediary.web.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

import com.leo.apediary.constant.Constants;
import com.leo.apediary.domain.User;
import com.leo.apediary.service.BusinessService;
import com.leo.apediary.service.impl.BusinessServiceImpl;
import com.leo.apediary.util.EncryptUtils;
import com.leo.apediary.util.IdGenertor;
import com.leo.apediary.util.WebUtils;
import com.mysql.jdbc.StringUtils;

/**
 * Servlet implementation class UserServlet
 */
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BusinessService bs = new BusinessServiceImpl();
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		
		if("userLogin".equals(action)) {
			userLogin(request, response);
		} else if("info".equals(action)) {
			infoUI(request, response);
		} else if ("save".equals(action)) {
			saveUser(request, response);
		}
	}
	
	// 保存用户信息
	private void saveUser(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 判断表单类型是不是  multipart/form-data
		if(!ServletFileUpload.isMultipartContent(request)) {
			throw new RuntimeException("The form is not multipart/form-data");
		}
		
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		List<FileItem> items = new ArrayList<FileItem>();
		upload.setFileSizeMax(1*1024*1024); // 上传文件大小限制：1M
		try {
			items = upload.parseRequest(request);
		} catch (FileUploadException e) {
//			request.setAttribute(Constants.ERROR_INFO, "上传出错，请重试~");
//			request.getRequestDispatcher("/client/user?action=infoUI").forward(request, response);
//			return;
			throw new RuntimeException(e);
		}
		
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(Constants.CURRENT_USER);
		if(user == null) {
			user = new User();
			user.setId(IdGenertor.genGUID());
		}
		for(FileItem item: items) {
			if(item.isFormField()) {
				processFormFiled(request, item, user);
			} else {
				processUploadFiled(request, response, item, user);
			}
		}
		
		int flag = bs.userUpate(user);
		if(flag < 0) {
			request.setAttribute(Constants.ERROR_INFO, "用户信息保存失败！");
			request.setAttribute(Constants.MAIN_PAGE, "user/userSave.jsp");
		}
		session.setAttribute(Constants.CURRENT_USER, user);
		response.sendRedirect(request.getContextPath()+"/client/home?re=zero");
	}

	// 上传字段：上传
	private void processUploadFiled(HttpServletRequest request, HttpServletResponse response,
			FileItem item, User user) throws ServletException, IOException {
		// 没有上传任何文件
		if (StringUtils.isNullOrEmpty(item.getName())) {
			return;
		}
		
		// 限制上传文件类型只能为图片
		if (!item.getContentType().startsWith("image")) {
			request.setAttribute(Constants.ERROR_INFO, "上传头像必须为图片，我的 Master！");
			request.setAttribute(Constants.MAIN_PAGE, "user/userSave.jsp");
			request.getRequestDispatcher("/home.jsp").forward(request, response);
			return;
		}
		
		// 存放路径：不要放在 WEB-INF 中
		String storeDir = getServletContext().getRealPath("/web/images/userImages");
		File rootDir = new File(storeDir);
		if (!rootDir.exists()) {
			rootDir.mkdirs();
		}
		
		// 备份原用户信息
		User userBackup = new User();
		WebUtils.copyProps(userBackup, user);
		
		// 处理文件名
		String fileName = item.getName();
		fileName = FilenameUtils.getName(fileName);
		if (fileName.equals(user.getImagename().split("_")[1])) {
			return;
		}
		fileName = user.getId()+"_"+fileName;
		user.setImagename(fileName);
		
		// 计算并生成子目录
		String chidDir = genChildDir(storeDir, fileName);
		user.setPath(chidDir);
		
		// 文件上传
		try {
			item.write(new File(rootDir, chidDir+"/"+fileName));
			delPreImage(userBackup); // 删除原头像文件
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	// 删除原头像文件和 hash 散列目录
	private void delPreImage(User userBackup) {
		String path = getServletContext().getRealPath("/web/images/userImages")+"/"+
				userBackup.getPath()+"/"+userBackup.getImagename();
		File f0 = new File(path);
		if (f0.exists()) {
			File f1 = f0.getParentFile();
			f0.delete();
			if (f1.exists()) {
				File f2 = f1.getParentFile();
				f1.delete();
				if (f2.exists()) {
					f2.delete();
				}
			}
		}
	}
	// 根据文件名 HashCode 散列生成两级子目录
	private String genChildDir(String realPath, String fileName) {
		int hashCode = fileName.hashCode();
		int dir1 = hashCode&0xf;
		int dir2 = (hashCode&0xf0) >> 4;
		String str = dir1 + File.separator + dir2;
		File file = new File(realPath, str);
		if(!file.exists()) file.mkdirs();
		return str;
	}
	// 普通字段：把数据封装到对象中
	private void processFormFiled(HttpServletRequest request, FileItem item, User user) {
		try {
			String fieldName = item.getFieldName();
			String fieldValue = item.getString(request.getCharacterEncoding());
			BeanUtils.setProperty(user, fieldName, fieldValue);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	// 设置用户信息 UI
	private void infoUI(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute(Constants.MAIN_PAGE, "user/userSave.jsp");
		request.getRequestDispatcher("/home.jsp").forward(request, response);
	}

	// 用户登录
	private void userLogin(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String remember = request.getParameter("remember");
		
		User user = bs.login(username, password);
		if(user == null) {
			request.setAttribute("user", new User(username, password));
			request.setAttribute(Constants.ERROR_INFO, "用户名或密码错误");
			request.getRequestDispatcher("/login.jsp").forward(request, response);
			return;
		}
		if("remember-me".equals(remember)) {
			rememberMe(username, password, request, response);
		}
		HttpSession session = request.getSession();
		session.setAttribute(Constants.CURRENT_USER, user);
		response.sendRedirect(request.getContextPath()+"/client/home?re=zero");
	}
	
	// 设置 cookie 记住用户名密码
	private void rememberMe(String username, String password, 
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Cookie cookie = new Cookie(Constants.LOGIN_COOKIE, 
				EncryptUtils.base64Encode(username)+"_"+password);
		cookie.setMaxAge(Integer.MAX_VALUE);
		cookie.setPath(request.getContextPath());
		response.addCookie(cookie);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
