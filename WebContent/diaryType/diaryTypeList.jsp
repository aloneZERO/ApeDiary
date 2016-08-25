<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/web/js/diaryType.js"></script>
<div class="data_list">
		<div class="data_list_title" style="float: left;width: 100%;height: 100%;">
			<img src="${pageContext.request.contextPath}/web/images/list_icon.png"/>
			日记类别列表
			<div class="diaryType_add pull-right">
				<button class="btn btn-mini btn-success" type="button" onclick="javascript:window.location='diaryType?action=preSave'">添加日记类别</button>
			</div>
		</div>
		<div class="clearfix"></div><br>
		<div>
			<table class="table table-hover table-striped">
			  <tr>
			  	<th>编号</th>
			  	<th>类别名称</th>
			  	<th>操作</th>
			  </tr>
			  <c:forEach var="diaryType" items="${diaryTypeList }">
			  	<tr>
			  		<td>${diaryType.diaryTypeId }</td>
			  		<td>${diaryType.typeName }</td>
			  		<td>
				  		<button class="btn btn-mini btn-primary" type="button" 
				  			onclick="javascript:window.location='diaryType?action=preSave&diaryTypeId=${diaryType.diaryTypeId }'">修改</button>&nbsp;
				  		<button class="btn btn-mini btn-danger" type="button" onclick="diaryTypeDel(${diaryType.diaryTypeId})">删除</button>
			  		</td>
			  	</tr>
			  </c:forEach>
			</table>
			<font id="error" color="red">${error}</font>
		</div>
</div>
