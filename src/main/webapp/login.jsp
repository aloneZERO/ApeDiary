<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>Sign In - Leo Diary</title>
	<link rel="icon" href="${pageContext.request.contextPath}/web/images/web-icon.png" type="image/x-icon"/>
	<link href="https://cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css" rel="stylesheet">
	<link href="https://cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap-theme.min.css" rel="stylesheet">
	<script src="https://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>
	<script src="https://cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
	
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/web/css/login.css" />
	<script type="text/javascript" src="${pageContext.request.contextPath}/web/js/login.js"></script>
</head>
<body>
  <c:if test="${sessionScope.currentUser != null}">
     <jsp:forward page="/client/home?re=zero"/>
  </c:if>
	<div class="container">
		<form role="form" id="loginForm" class="form-signin" method="post"
		  action="${pageContext.request.contextPath}/client/user?action=userLogin">
			<h1 class="form-signin-heading">猿日记</h1>
			<br>
			<div class="form-group">
				<div class="input-group">
					<div class="input-group-addon">
						<img alt="Username" src="${pageContext.request.contextPath}/web/images/user_icon.png">
					</div>
					<input  type="text" style="margin: 0px;" class="form-control"
					id="username" name="username" value="${user.username}" placeholder="猿名..."/>
				</div>
			</div>
			<div class="form-group">
				<div class="input-group">
					<div class="input-group-addon">
						<img alt="password" src="${pageContext.request.contextPath}/web/images/password_icon.png">
					</div>
					<input  type="password" style="margin-bottom: 0px;" class="form-control"
					id="password" name="password" value="${user.password}" placeholder="猿码..." />
				</div>
			</div>
			<div class="form-group">
				<label class="sr-only" for="remember">Remember me</label>
				<input type="checkbox" id="remember" name="remember" value="remember-me">&nbsp;眼熟我&nbsp;&nbsp;
				<font id="error" color="red">${error}</font>
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
