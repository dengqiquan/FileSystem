$(document).ready(function() {
	$("#saveObjForm").validationEngine() 
})
function saveDir(parentIndex){
	alert("aaa")
	var index = layer.load(3, {shade: [0.5,'#B3B3B3']});
	if(!jQuery('#saveObjForm').validationEngine('validate')){
		layer.close(index);
		return false;	
	}
	var url = "saveDir";
	var id = $("input[name='id']").val();
	if(id!=null && id>0){
		url = "updateDir";
	}
	var params = $("#saveObjForm").serialize();
	$.post(url,params,function(data){
		if (data.head.resultCode == 200) {
			if(data.body.state==1){
				layer.close(index);
				layer.msg("操作成功！");
				parent.list();
				setTimeout(() => {
					parent.layer.close(parentIndex);	
				}, 1500);
			}else if(data.body.state==-100){
				layer.msg("该目录已经存在！");
				layer.close(index);
				return false;
			}else {
				layer.msg("操作失败！");
				layer.close(index);
				return false;
			}
		} else {
			layer.msg("请求失败!");
		}
	 },'json');
}