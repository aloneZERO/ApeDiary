<%@page import="com.leo.model.User"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%
	/* Rmember-me涉及到两种场景
	 * 1.用户直接登录，此时读取cookie
	 * 2.用户返回登录页面
	 *
	 * 根据请求是否存在user对象来判断是哪种场景
	 */
	if(request.getAttribute("user")==null) {
		String userName = null;
		String password = null;
		
		Cookie[] cookies = request.getCookies();
		for(int i=0; cookies!=null&&i<cookies.length; i++) {
			if(cookies[i].getName().equals("user")) {
				userName = cookies[i].getValue().split("-")[0];
				password = cookies[i].getValue().split("-")[1];
			}
		}
		if("null".equals(userName)||userName==null) {
			userName = "";
		}
		if("null".equals(password)||password==null) {
			password = "";
		}
		pageContext.setAttribute("user", new User(userName,password));
	}
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>Sign In - Online Diary</title>
	<link rel="icon" href="web/images/web-icon.png" type="image/x-icon"/>
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.min.css" />
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/bootstrap/css/bootstrap-theme.min.css" />
	<script type="text/javascript" src="${pageContext.request.contextPath}/bootstrap/js/jquery-1.11.1.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.min.js"></script>
	
	<link type="text/css" rel="stylesheet" href="web/css/login.css" />
	<script type="text/javascript" src="web/js/login.js"></script>
</head>
<body>
	<div class="container">
		<form role="form" id="loginForm" class="form-signin" action="login" method="post">
			<h1 class="form-signin-heading">屌丝日记</h1>
			<br>
			<div class="form-group">
				<div class="input-group">
					<div class="input-group-addon">
						<img alt="Username" src="web/images/user_icon.png">
					</div>
					<input  type="text" style="margin: 0px;" class="form-control" id="userName" name="userName" value="${user.getUserName()}" placeholder="屌丝名...">
				</div>
			</div>
			<div class="form-group">
				<div class="input-group">
					<div class="input-group-addon">
						<img alt="password" src="web/images/password_icon.png">
					</div>
					<input  type="password" style="margin-bottom: 0px;" class="form-control" id="password" name="password" value="${user.getPassword()}" placeholder="屌丝码..." >
				</div>
			</div>
			<div class="form-group">
				<label class="sr-only" for="remember">Remember me</label>
				<input type="checkbox" id="remember" name="remember" value="remember-me">&nbsp;眼熟我
				&nbsp;&nbsp;<font id="error" color="red">${error}</font>
			</div>
			<div class="row">
				<div class="login-btn col-md-5">
					<button class="form-control btn btn-primary" type="submit">登录</button>
				</div>
				<div class="reset-btn col-md-5 col-md-offset-2">
					<button class="form-control btn btn-primary" type="button" id="reset">重置</button>
				</div>
			</div>
			<p align="center" style="padding-top: 15px;">
				Copyright&copy;&nbsp;&nbsp;<a href="http://github.com/aloneZERO" target="_blank">justZERO</a>版权所有
			</p>
		</form>
	</div>
</body>
</html>
