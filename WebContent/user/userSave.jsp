<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/web/js/user.js"></script>
<div class="data_list">
	<div class="data_list_title">
		<img src="${pageContext.request.contextPath}/web/images/user_edit_icon.png"/>
		个人信息设置
	</div>
	<div class="row" style="padding-top: 20px;">
		<div class="col-md-5">
			<img src="${pageContext.request.contextPath}/web/images/userImages/${currentUser.path}/${currentUser.imagename}"
			alt="user image" style="width:100%;border-radius:5px;"/>
		</div>
		<div class="col-md-7">
			<form action="${pageContext.request.contextPath}/client/user?action=save" method="post"
			 enctype="multipart/form-data" id="userForm">
				<table style="width: 100%">
					<tr>
						<td width="20%">更换头像：</td>
						<td><input type="file" name="userImage" value="${currentUser.imagename}"/></td>
					</tr>
					<tr><td>&nbsp;</td><td>&nbsp;</td></tr>
					<tr>
						<td>个人昵称：</td>
						<td><input type="text" class="form-control" id="nickname"
						  name="nickname" value="${currentUser.nickname}" style="margin-top:5px;height:30px;"/>
						</td>
					</tr>
					<tr><td>&nbsp;</td><td>&nbsp;</td></tr>
					<tr>
						<td valign="top">个人心情：</td>
						<td>
							<textarea class="form-control" name="description" rows="10">${currentUser.description}</textarea>
						</td>
					</tr>
					<tr><td>&nbsp;</td><td>&nbsp;</td></tr>
					<tr>
						<td><button type="submit" class="btn btn-primary">保存</button></td>
						<td><button class="btn btn-primary" type="button" onclick="javascript:history.back()">返回</button></td>
					</tr>
					<tr><td>&nbsp;</td><td>&nbsp;</td></tr>
					<tr>
						<td colspan="2"><font color="red" id="error">${error}</font></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</div>
