/**
 * 复选框全选
 * 2016/9/30
 * @param ckBoxElement
 * @param ckBoxName
 * @return
 */
function checkAll(ckBoxElement, ckBoxName) {
	var checkStatus = $(ckBoxElement).prop('checked');
	if (checkStatus) {
		$(":checkbox[name='" + ckBoxName + "'][disabled!='disabled']").prop('checked', true);
	} else {
		$(":checkbox[name='" + ckBoxName + "'][disabled!='disabled']").prop('checked', false);
	}
}

/**
 * 全选框选中非选中
 */
function checkSelect(ckBoxElement, ckBoxName, len){
	var arrCheck = $("input[name='"+ckBoxName+"']:checked");
	if(arrCheck.length == len){
		$('#'+ckBoxElement).prop('checked', true);
	} else {
		$('#'+ckBoxElement).prop('checked', false);
	}
}

/**
 * 检查复选框是否至少有一个被选中
 * 
 * @param ckBoxName
 * @return
 */
function checkCkBoxStatus(ckBoxName) {
	var ckFlag = false;

	var arrCheck = $("input[name='"+ckBoxName+"']:checked");
	if(arrCheck.length > 0){
		ckFlag = true;
	}
	return ckFlag;
}

function doAjax(url,date,fn){
	$.ajax({
		url: url,
		data:date,
		async: false,
		type: "POST",
		dataType: "json",
		cache: false,
		success: function(data) {
			fn(data);
		}
	});
}