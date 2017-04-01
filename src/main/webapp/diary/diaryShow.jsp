<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/web/js/diary.js"></script>
<div class="data_list">
		<div class="data_list_title">
			<img src="${pageContext.request.contextPath}/web/images/diary_show_icon.png"/>
			日记阅读
		</div>
		<div>
			<div class="diary_title"><h3>${diary.title}</h3></div>
			<div class="diary_info">
				发布时间：『 <fmt:formatDate value="${diary.releaseDate}" type="date" pattern="yyyy-MM-dd HH:mm:ss"/> 』&nbsp;&nbsp;
				日记类别：${diary.category.name}
			</div>
			<div class="diary_content">${diary.content}</div>
			<div class="diary_action">
				<button class="btn btn-primary" type="button"
		      onclick="javascript:window.location='${pageContext.request.contextPath}/client/diary?action=writeUI&diaryId=${diary.id}'">
		                    修改日记
	      </button>
				<button class="btn btn-primary" type="button" onclick="javascript:history.back()">返回</button>
				<button class="btn btn-danger" type="button" onclick="diaryDel('${diary.id}')">删除日记</button>
				&nbsp;&nbsp;<font id="error" color="red">${error}</font>
			</div>
		</div>
</div>
