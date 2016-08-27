$(document).ready(function(){
	$('#userForm').submit(function(){
		if($('#nickName').val()==null||$('#nickName').val().trim().length==0) {
			$('#error').html("昵称不能为空！");
			return false;
		}
		return true;
	});
});