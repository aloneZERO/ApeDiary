<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<div class="data_list">
		<div class="data_list_title">
		<img src="${pageContext.request.contextPath}/web/images/diary_show_icon.png"/>
		日记阅读</div>
		<div>
			<div class="diary_title"><h3>${diary.getTitle() }</h3></div>
			<div class="diary_info">
				发布时间：『 <fmt:formatDate value="${diary.getReleaseDate() }" type="date" pattern="yyyy-MM-dd HH:mm:ss"/>』&nbsp;&nbsp;日记类别：${diary.typeName}
			</div>
			<div class="diary_content">
				${diary.getContent()}
			</div>
			<div class="diary_action">
				<button class="btn btn-primary" type="button" onclick="">修改日记</button>
				<button class="btn btn-primary" type="button" onclick="javascript:history.back()">返回</button>
				<button class="btn btn-danger" type="button" onclick="">删除日记</button>
			</div>
		</div>
</div>