package com.leo.apediary.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.leo.apediary.constant.Constants;
import com.leo.apediary.domain.User;
import com.leo.apediary.service.BusinessService;
import com.leo.apediary.service.impl.BusinessServiceImpl;
import com.leo.apediary.util.EncryptUtils;

/**
 * 登录过滤器
 * 自动登录和未登录请求过滤
 */
public class AutoLoginFilter implements Filter {
	
	private BusinessService bs = new BusinessServiceImpl();
	
	public void destroy() {}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest  request;
        HttpServletResponse response;
        try {
            request = (HttpServletRequest) req;
            response = (HttpServletResponse) res;
        } catch (ClassCastException e) {
            throw new ServletException("non-HTTP request or response");
        }
        
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute(Constants.CURRENT_USER);
        // session 中无用户实例：当前没有登录
        if (currentUser == null) { // 从 cookie 中查找登录信息
        	Cookie[] cookies = request.getCookies();
        	Cookie loginCookie = null;
        	for (int i=0; cookies!=null&&i< cookies.length; i++) {
				if (cookies[i].getName().equals(Constants.LOGIN_COOKIE)) {
					loginCookie = cookies[i];
					break;
				}
			}
        	if (loginCookie != null) {
        		String value = loginCookie.getValue();
        		String[] info = value.split("_");
    			String username = EncryptUtils.base64Decode(info[0]);
    			String password = value.substring(info[0].length()+1, value.length());
    			// 再进行一次数据库数据验证，防止 cookie 被恶意修改过
				User user = bs.login(username, password);
				if (user != null) {
					session.setAttribute(Constants.CURRENT_USER, user);
				}
        	}
        }
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {}

}
