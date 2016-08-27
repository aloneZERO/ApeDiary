package com.leo.web;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase.FileSizeLimitExceededException;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.leo.dao.UserDao;
import com.leo.model.User;
import com.leo.util.DateUtil;
import com.leo.util.DbUtil;
import com.leo.util.PropertiesUtil;
import com.leo.util.StringUtil;

public class UserServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private UserDao userDao = new UserDao();

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
		if("preSave".equals(action)) {
			userPerSave(request,response);
		}else if("save".equals(action)) {
			userSave(request, response);
		}
	}
	
	private void userPerSave(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("mainPage", "user/userSave.jsp");
		request.getRequestDispatcher("main.jsp").forward(request, response);
	}
	
	/**
	 * 用户信息保存，涉及到 Jsp/Servlet 文件上传的学习
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void userSave(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 得到上传头像文件的保存目录：该方法获取的运行时 Servlet 的绝对路径
		String imagePath = this.getServletContext().getRealPath("/web/images/userImages/");
//		System.out.println(imagePath);
		
		// 上传时生成的临时文件存放目录
        File tmpFile = new File(PropertiesUtil.getValue("imagePath")+"temp");
        if (!tmpFile.exists()) {
            tmpFile.mkdir();  // 创建临时目录
        }
		
		// 使用 Apache 文件上传组件处理文件上传步骤：
		// 1.创建一个 DiskFileItemFactory 工厂
		DiskFileItemFactory factory = new DiskFileItemFactory();
		
		// 设置工厂的缓冲区的大小，当上传的文件大小超过缓冲区的大小时，就会生成一个临时文件存放到指定的临时目录当中。
		factory.setSizeThreshold(1024*100); // 设置缓冲区的大小为100KB，如果不指定，那么缓冲区的大小默认是10KB
		// 设置上传时生成的临时文件的存放目录
        factory.setRepository(tmpFile);
		
		// 2.创建一个文件上传解析器
		ServletFileUpload upload = new ServletFileUpload(factory);
		
		// 监听文件上传进度：可用于制作上传进度条
		upload.setProgressListener(new ProgressListener() {
			@Override
			public void update(long pBytesRead, long pContentLength, int arg2) {
//				System.out.println("File size: " + pContentLength + ", Completed: " + pBytesRead);
			}
		});
		
		upload.setHeaderEncoding("UTF-8");  // 解决上传文件名的中文乱码
		// 3.判断提交上来的数据是否是上传表单的数据
		if(!ServletFileUpload.isMultipartContent(request)){
			// 按照传统方式获取数据。这里的处理方式：返回错误提示
			request.setAttribute("eroor", "发生未知名错误，请稍后重试！");
			request.setAttribute("mainPage", "user/userSave.jsp");
			request.getRequestDispatcher("main.jsp").forward(request, response);
		}
		
		// 设置上传单个文件的大小的最大值，目前是设置为 1024*1024 字节，也就是 1MB
        upload.setFileSizeMax(1024*1024);
        // 设置上传文件总量的最大值，最大值 = 同时上传的多个文件的大小的最大值的和，目前设置为 10MB
//      upload.setSizeMax(1024*1024*10);
		
		/* 4.使用  ServletFileUpload 解析器解析上传数据，
		 * 解析结果返回的是一个 List<FileItem> 集合，
		 * 每一个 FileItem 对应一个 Form 表单的输入项。
		 */
		List<FileItem> items = null;
		try {
			items = upload.parseRequest(request);
		}catch(FileSizeLimitExceededException fsle) {
			request.setAttribute("error", "上传的图片文件请限制在 1MB 以内！");
			request.setAttribute("mainPage", "user/userSave.jsp");
			request.getRequestDispatcher("main.jsp").forward(request, response);
		} catch (FileUploadException e) {
			e.printStackTrace();
		} 
		
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("currentUser");
		boolean imageChange = false; // 标记头像是否被更改
		for(FileItem item:items) {
			// 如果 fileitem 中封装的是普通输入项的数据（字段名和字段值）
			if(item.isFormField()) {
				String fieldName = item.getFieldName();
				if("nickName".equals(fieldName)) {
					// 解决普通输入项数据的中文乱码问题
					user.setNickName(item.getString("utf-8"));
				}
				if("mood".equals(fieldName)) {
					user.setMood(item.getString("utf-8"));
				}
			}else if(StringUtil.isNotEmpty(item.getName())) { // 如果 fileitem 中封装的是上传文件
				imageChange = true;
				
				/* 注意：不同的浏览器提交的文件名是不一样的，
				 * 有些浏览器提交上来的文件名是带有路径的，如：  c:\a\b\1.txt，
				 * 而有些只是单纯的文件名，如：1.txt。
				 */
				String fileName = item.getName(); // 获取上传文件名
				String imageName = DateUtil.getCurrentDateStr(); // 以当前时间命名上传文件
				// 根据文件后缀名判断上传文件是否为图片文件
				if(fileName.endsWith("jpg")||fileName.endsWith("png")||fileName.endsWith("bmp")) {
					String imageWholeName = imageName+"."+fileName.split("\\.")[1]; // 完整上传文件名
					user.setImageName(imageWholeName);
					String imageFilePath = imagePath+imageWholeName;
					try {
						item.write(new File(imageFilePath)); // 保存上传文件
                        item.delete(); // 删除处理文件上传时生成的临时文件
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else {
					request.setAttribute("error", "上传的头像图片仅支持 jpg, png, bmp 格式！");
					request.setAttribute("mainPage", "user/userSave.jsp");
					request.getRequestDispatcher("main.jsp").forward(request, response);
				}
			}
		}
		if(!imageChange) {
			user.setImageName(user.getImageName().replaceFirst(PropertiesUtil.getValue("userImage"),""));
		}
		
		// 连接数据库，更新用户信息
		Connection conn = null;
		try {
			conn = DbUtil.getConnection();
			int saveNum = userDao.userUpdate(conn, user);
			if(saveNum > 0) {
				user.setImageName(PropertiesUtil.getValue("userImage")+user.getImageName());
				session.setAttribute("currentUser", user);
				request.getRequestDispatcher("main?all=true").forward(request, response);
			}else {
				request.setAttribute("currentUser", user);
				request.setAttribute("error", "用户信息保存失败！");
				request.setAttribute("mainPage", "user/userSave.jsp");
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
	
}
