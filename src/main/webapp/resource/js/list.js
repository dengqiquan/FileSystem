function addOrEdit(id) {
	var url = "edit";
	if (id != null) {
		url = url + "?id=" + id;
	}
	layer.open({
		content : url, // iframe的url
		type : 2,
		title : '添加/修改',
		shade : 0.8,
		area : [ '100%', '100%' ],
		btn : [ '确定', '取消' ],
		yes : function(index, layero) {
			var iframeWin = window[layero.find('iframe')[0]['name']];
			iframeWin.save(index);
			setTimeout(function() {
				$('#searchForm').submit();
			}, 1000);
		},
		cancel : function(index) {
		}
	});
}

/**
 * 调用分页方法
 * 
 * @param number 当前页
 * @param count  总条数
 * @param paginationDivID 分页divID
 * @param searchFormId 列表查询form标签ID
 * @param inputPageNumberName form中pageNumber的input标签的name
 */
function paginationFn(number, count, paginationDivID) {
	if (count > 0) {
		$('#' + paginationDivID).pagination({
			pages : count,
			cssStyle : 'light-theme',
			currentPage : number,
			onPageClick : function(pageNumber, event) {
				$("input[name='pageNumber']").val(pageNumber);
				$('#searchForm').submit();
			}
		});
	}
}

function del(id) {
	parent.layer.open({
	    content: '你是确认删除？',
	    btn: ['确认', '取消'],
	    shadeClose: false,
	    yes: function(){
	    	parent.layer.open({content: '你点了确认', time: 1});
	        var url = 'deleteById';
			$.post(url,{id:id},function(data){
				if(data.state == 1){			
					layer.msg(data.msg);
					setTimeout(function () {
						$('#searchForm').submit();
					}, 1000);
				}else{
					layer.msg(data.msg);
			 	}
			},'json');
	    }
	});
}

/**
 * 复选框全选
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

$(function() {
	var pageNumber = $("#pageNumber").val();
	var pageCount = $("#pageCount").val();
	paginationFn(pageNumber, pageCount, "light-pagination");
})