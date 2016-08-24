<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/web/js/diary.js"></script>
<div class="data_list">
		<div class="data_list_title">
			<img src="${pageContext.request.contextPath}/web/images/diary_add_icon.png"/>
			写日记
		</div>
		<form action="diary?action=save&diaryId=${diary.getDiaryId()}" method="post" onsubmit="return checkForm()" id="diaryForm">
			<div class="diary_title form-group">
				<label class="sr-only" for="title">日记标题</label>
				<input type="text" class="input-lg" id="title" name="title" value="${diary.title}" placeholder="日记标题..." style="height: 40px;"/>
			</div>
			<div class="form-group">
				<textarea class="ckeditor form-control" id="content" name="content">${diary.content}</textarea>
			</div>
			<div class="diary_type form-group">
				<select id="typeId" name="typeId" class="form-control">
					<option value="">请选择日记类别...</option>
					<c:forEach var="diaryTypeCount" items="${diaryTypeCountList }">
						<option value="${diaryTypeCount.diaryTypeId }" ${diaryTypeCount.diaryTypeId==diary.typeId?'selected':''} >${diaryTypeCount.typeName}</option>
					</c:forEach>
				</select>
			</div>
			<div class="form-group row" style="margin-top: 50px;">
				<div class="col-md-2">
					<input type="submit" class="btn btn-primary form-control" value="保存"/>
				</div>
				<div class="col-md-2">
					<button class="btn btn-primary form-control" type="button" onclick="javascript:history.back()">返回</button>
				</div>
				<font id="error" color="red">${error}</font>
			</div>
		</form>
</div>