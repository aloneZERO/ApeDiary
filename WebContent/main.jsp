<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>Home - Online Diary</title>
	<link rel="icon" href="${pageContext.request.contextPath}/web/images/web-icon.png" type="image/x-icon"/>
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.min.css" />
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/bootstrap/css/bootstrap-theme.min.css" />
	<script type="text/javascript" src="${pageContext.request.contextPath}/bootstrap/js/jquery-1.11.1.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.min.js"></script>
	
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/web/css/main.css" />
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/web/css/diary.css" />
</head>
<body>
	<!-- 导航栏 start -->
	<nav class="navbar navbar-inverse" role="navigation" id="navbar">
		<div class="container-fluid" id="navbar-container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#diary-navbar-collapse">
					<span class="sr-only">Toggle navigation</span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#" id="navbar-brand">
					<img alt="Brand" src="${pageContext.request.contextPath}/web/images/diary_brand.png">
				</a>
			</div>
			<div class="collapse navbar-collapse" id="diary-navbar-collapse">
				<ul class="nav navbar-nav" id="navbar-nav">
					<li class="active">
						<a href="#"><i class="glyphicon glyphicon-home"></i>&nbsp;主页</a>
					</li>
					<li>
						<a href="#"><i class="glyphicon glyphicon-pencil"></i>&nbsp;写日记</a>
					</li>
					<li>
						<a href="#"><i class="glyphicon glyphicon-book"></i>&nbsp;分类管理</a>
					</li>
					<li>
						<a href="#"><i class="glyphicon glyphicon-user"></i>&nbsp;个人中心</a>
					</li>
				</ul>
				<form name="myForm" class="navbar-form navbar-right" role="search" method="post" action="search">
					<div class="form-group">
						<input type="text" class="form-control" id="s_title" name="s_title" placeholder="追忆向...">
						<button type="submit" class="btn btn-default" onkeydown="if(event.keyCode==13) myForm.submit()">
							<i class="glyphicon glyphicon-search"></i>&nbsp;搜索日记
						</button>
					</div>
				</form>
			</div>
		</div>
	</nav>
	<!-- 导航栏 end -->
	
	<!-- 主体内容 start -->
	<div class="container">
		<div class="row">
			<!-- 日记列表 start -->
			<div class="col-md-9">
				<jsp:include page="${mainPage}"></jsp:include>
			</div>
			<!-- 日记列表 end -->

			<div class="col-md-3">
				<!-- 个人中心 start -->
				<div class="data_list">
					<div class="data_list_title">
						<img src="${pageContext.request.contextPath}/web/images/user_icon.png"/>
						个人中心
					</div>
				</div>
				<!-- 个人中心 end -->

				<!-- 按日记类别 start -->
				<div class="data_list">
					<div class="data_list_title">
						<img src="${pageContext.request.contextPath}/web/images/byType_icon.png"/>
						按日记类别
					</div>
					<div class="datas">
						<ul>
							<c:forEach var="diaryTypeCount" items="${diaryTypeCountList}">
								<li>
									<span><a href="#">${diaryTypeCount.typeName}(${diaryTypeCount.diaryCount})</a></span>
								</li>
							</c:forEach>
						</ul>
					</div>
				</div>
				<!-- 按日记类别 end -->

				<!-- 日记日期 start -->
				<div class="data_list" style="margin-bottom: 15px;">
					<div class="data_list_title">
						<img src="${pageContext.request.contextPath}/web/images/byDate_icon.png"/>
						按日记日期
					</div>
					<div class="datas">
						<ul>
							<c:forEach var="diaryCount" items="${diaryCountList}">
								<li>
									<span><a href="#">${diaryCount.releaseDateStr}(${diaryCount.diaryCount})</a></span>
								</li>
							</c:forEach>
						</ul>
					</div>
				</div>
				<!-- 日记日期 end -->
			</div>
		</div>
	</div>
	<!-- 主体内容 end -->

</body>
</html>
