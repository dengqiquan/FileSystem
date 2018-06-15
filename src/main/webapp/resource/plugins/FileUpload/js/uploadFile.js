(function($){
    $.fn.FileUpload = function(opts){
        opts = $.extend({},$.fn.FileUpload.defaults,opts) ;
        initHtml(opts);
    } ;
    $.fn.FileUpload.defaults = {
        mainID : "#fileId",
        uploadFileType:"",
        list:[]
    };
    
    function initHtml (opts){
    	var html = "";
    	html+="<div class=\"panel panel-default\">";
    	html+="<div class=\"panel-body\">";
    	html+="<div style=\"margin-bottom: 10px;cursor: pointer;width: 90px;float: left;\">";
    	html+="<button type=\"button\" class=\"btn btn-primary\" style=\"position: absolute;\" >选择文件</button>";
    	html+="<input type=\"file\" value=\"选择文件\"  multiple='multiple' class=\"btn btn-github btn-success fileIput\" id=\"uploadFiles\" style=\"cursor: pointer;\"/><br/>";
    	html+="</div><div style=\"float: left;\"> ";
    	html+="<button type=\"button\" class=\"btn btn-danger\" style=\"position: absolute;\" onclick=\"removeFile('ids')\">删除文件</button>";
    	html+="</div></div>";
    	html+="<table class=\"table table-hover\">";
    	html+="<thead id=\"fileThead\">";
    	html+="<tr>";
    	html+="<th style=\"width: 5%;\"><input type=\"checkbox\" onclick=\"checkAll(this,'ids')\" /></th>";
    	html+="<th style=\"width: 120px;\">文件预览</th>";
    	html+="<th>文件名称</th><th>文件大小</th></tr></thead>";
    	html+="<tbody id=\"fileBody\"></tbody></table>";
    	html+="<div style=\"padding: 10px;display: none;\" id=\"fileProgressDIV\"><div class=\"progress\" >";
    	html+="<div id=\"fileProgress\" class=\"progress-bar\" role=\"progressbar\" aria-valuenow=\"60\" aria-valuemin=\"0\" aria-valuemax=\"100\" style=\"width: 60%;\">60</div></div>";
    	html+="</div></div>";
    	$(opts.mainID).append(html);
    	
    	$("#uploadFiles").change(function(){
    		var fileList = document.getElementById("uploadFiles").files;
    		for(var i=0;i<fileList.length;i++){
    			if(opts.uploadFileType.indexOf(fileList[i].name.substring(fileList[i].name.lastIndexOf('.') + 1))>0)
	    			uploadTeamSpiritImg(fileList[i],function(xhr){
	    				if(xhr.responseText!=null && xhr.responseText!=""){
	    					$("#fileProgressDIV").hide();
	    					var obj =  eval('(' + xhr.responseText + ')');
	    					//layer.msg(obj.msg);
	    					if(obj.state==1){
	    						var file = obj.file;
	    						var html = "<tr id='"+file.id+"'>";
	    						html+="<td><input type=\"checkbox\" name=\"ids\" value='"+file.id+"'/><input type=\"hidden\" name=\"fileIds\" value=\""+file.id+"\" /></td>";
								if(file.fileType=='image'){
									html+="<td><img src=\"/downloadFile?id="+file.id+"\"  class=\"img-thumbnail\"/></td>"
								}else {
									html+="<td><img src=\"/resource/plugins/FileUpload/images/"+file.fileType+".png\"  class=\"img-thumbnail\"/></td>"
								}
	    						html+="<td>"+file.fileName+"</td><td>"+file.fileSize+"</td></tr>";
	    						$("#fileBody").append(html);
	    					}else {
	    						layer.msg("文件上传失败");
							}
	    				}else{
	    					layer.msg("文件上传失败");
	    				}
	    			});
    			else {
					layer.msg("只允许上传："+opts.uploadFileType);
					return false;
				}
    		}
    	});
    }
    function initData (opts){
    }
    //文件上传
    function uploadTeamSpiritImg(file,fn) {
    	(function(file) {
    		var xhr = new XMLHttpRequest();
    		if (xhr.upload) {
    			xhr.upload.addEventListener("progress", uploadProgress, false);  
    			// 文件上传成功或是失败
    			xhr.onreadystatechange = function(e) {
    				if (xhr.readyState == 4) {
    					if (xhr.status == 200) {
    						fn(xhr);
    					} else {
    						alert('上传失败！');
    					}
    				}
    			};
    			// 开始上传
    			var url = "/uploadFile?fileName="+encodeURIComponent(file.name)+"&fileSize="+file.size+"&fileType="+file.type;
    			xhr.open("POST", url, true);
    			xhr.send(file);
    		}	
    	})(file);	
    }
    
    function uploadProgress(evt) {  
        if (evt.lengthComputable) {  
        	$("#fileProgressDIV").show();
        	var percentComplete = Math.round(evt.loaded * 100 / evt.total);  
        	$("#fileProgress").attr("aria-valuenow",percentComplete);  
        	$("#fileProgress").width(percentComplete+"%");  
        	$("#fileProgress").text(percentComplete+"%");  
        }
    }  
})(jQuery) ;

function removeFile(ckBoxName){
	$("input[name='"+ckBoxName+"']:checked").each(function(){
		$("#"+$(this).val()).remove();
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
