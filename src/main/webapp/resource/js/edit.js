$(document).ready(function() {
	$("#saveObjForm").validationEngine() 
})
function save(parentIndex){
	var index = layer.load(3, {shade: [0.5,'#B3B3B3']});
	if(!jQuery('#saveObjForm').validationEngine('validate')){
		layer.close(index);
		return false;	
	}
	var url = "save";
	var params = $("#saveObjForm").serialize();
	$.post(url,params,function(data){
		layer.close(index);
		layer.msg("保存成功！");
		layer.close(parentIndex);
	 },'json');
}