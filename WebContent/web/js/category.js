$(document).ready(function(){
	// 日记类别保存的表单验证
	$('#categoryForm').submit(function(){
		if($('#categoryName').val()==null||$('#categoryName').val().trim().length==0) {
			$('#error').html("类别名称不能为空！");
			return false;
		}
		return true;
	});
});

//日记类别删除确认
function categoryDel(categoryId) {
	if(confirm("确定要删除此日记类别吗？")) {
		window.location="category?action=del&categoryId="+categoryId;
	}
}