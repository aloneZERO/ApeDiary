$(document).ready(function() {
	// 登录表单初步验证,判断表单信息是否为空
	$('#loginForm').submit(function() {
		if($('#username').val()==null || $('#username').val().trim().length==0) {
			$('#error').html("用户名不能为空");
			return false;
		}
		if($('#password').val()==null || $('#password').val().trim().length==0) {
			$('#error').html("密码不能为空");
			return false;
		}
		return true;
	});
	
	// 表单信息重置
	$('#reset').click(function() {
		$('#username').val("");
		$('#password').val("");
		$('#error').html("");
	});
});
