$(document).ready(function(){
	// 日记类别保存的表单验证
	$('#diaryTypeForm').submit(function(){
		if($('#typeName').val()==null||$('#typeName').val().trim().length==0) {
			$('#error').html("类别名称不能为空！");
			return false;
		}
		return true;
	});
});

//日记类别删除确认
function diaryTypeDel(diaryTypeId) {
	if(confirm("确定要删除此日记类别吗？")) {
		window.location="diaryType?action=delete&diaryTypeId="+diaryTypeId;
	}
}