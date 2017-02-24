$(document).ready(function(){
	// 日记保存时的表单验证
	$('#diaryForm').submit(function(){
		if($('#title').val()==null||$('#title').val().trim().length==0){
			$('#error').html("标题不能为空！");
			return false;
		}
		if($('#categoryId').val()==null||$('#categoryId').val().trim().length==0){
			$('#error').html("请选择日记类别！");
			return false;
		}
		return true;
	});
});

// 日记删除时的弹窗确认
function diaryDel(diaryId){
	if(confirm("确定要删除此日记吗？")){
		window.location="diary?action=del&diaryId="+diaryId;
	}
}
