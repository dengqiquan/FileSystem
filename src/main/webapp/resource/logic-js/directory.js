$(function() {
	list();
	$("#uploadFiles").change(function(){
		var index = layer.load(3, {shade: [0.5,'#B3B3B3']});
		var fileList = document.getElementById("uploadFiles").files;
		for(var i=0;i<fileList.length;i++){
			uploadFile(fileList[i],function(xhr){
				if(xhr.responseText!=null && xhr.responseText!=""){
					var obj =  eval('(' + xhr.responseText + ')');
					if(obj.head.resultCode == 200){
						if(obj.body.state == 1){
							list();
						}else if(obj.body.state == -100){
							layer.alert('文件格式不正确!', {icon: 2})
						}
					}else {
						layer.alert('文件上传失败!', {icon: 2})
					}
				}else{
					layer.alert('文件上传失败!', {icon: 2})
				}
				layer.close(index);
			});
		}
	});
})
function list() {
	var url = "list";
	var data = {};
	data.parentId = $("input[name='parentId']").val();
	if (data.parentId == 0) {
		$("#upFile").hide();
		$("#back").hide();
	}
	doAjax(url,data,function(data) {
		if (data.head.resultCode == 200) {
			var body = data.body;
			var directories = body.directories;
			var files = body.files;
			var directory = body.directory;
			$("input[name='parentId']").val(directory.id);
			$("input[name='curPath']").val(directory.curPath);
			$("#tb").empty();
			var html = "";
			for (var i = 0; i < directories.length; i++) {
				var dir = directories[i];
				html += "<tr class='active'>";
				html += "<td><a href='listPage?parentId=" + dir.id + "'>" + dir.name + "</a></td>";
				html += "<td>目录</td>";
				html += "<td>--</td>";
				html += "<td>" + dir.createUserName + "</td>";
				html += "<td>" + dir.createTime + "</td>";
				html += "<td><a href='javascript:void(0)' title='编辑' onclick='addDir("+dir.id+ ")' class='btn btn-primary btn-xs'><span class='glyphicon glyphicon-edit'></span></a>&nbsp;&nbsp;";
				html += "<a href='javascript:void(0)' title='删除' onclick='delDir("+ dir.id + ")' class='btn btn-danger btn-xs'><span class='glyphicon glyphicon-trash'></span></a></td>";
				html += "<tr>";
			}
			
			for(var i=0;i<files.length;i++){
				var file = files[i];
				html+="<tr class='active'>";
				html += "<td style=\"text-align: left;\">" + file.name + "</td>";
				html += "<td>"+file.type+"</td>";
				html += "<td>"+file.size+"</td>";
				html += "<td>" + file.createUserName + "</td>";
				html += "<td>" + file.createTime + "</td>";
				html+="<td>";
				if(file.type=="mp3"){
					html += "<a href='javascript:void(0)' onclick='playAudio(\""+file.name+"\",\""+file.previewPath+"\")' title='预览'  class='btn btn-primary btn-xs'><span class='glyphicon glyphicon-play-circle'></span></a>&nbsp;&nbsp;";
				}else if(file.type=="mp4"){
					html += "<a href='javascript:void(0)' onclick='playMp4(\""+file.name+"\",\""+file.previewPath+"\")' title='预览'  class='btn btn-primary btn-xs'><span class='glyphicon glyphicon-play-circle'></span></a>&nbsp;&nbsp;";
				}else{
					html += "<a href='javascript:void(0)' onclick='viewFile("+file.id+ ",\""+file.name+"\")' title='预览'  class='btn btn-primary btn-xs'><span class='glyphicon glyphicon-eye-open'></span></a>&nbsp;&nbsp;";	
				}
				
				html += "<a href='/views/sysFile/downloadFile?id="+file.id+"' title='下载'  class='btn btn-primary btn-xs'><span class='glyphicon glyphicon-floppy-save'></span></a>&nbsp;&nbsp;";
				html += "<a href='javascript:void(0)' title='删除' onclick='delFile("+ file.id + ")' class='btn btn-danger btn-xs'><span class='glyphicon glyphicon-trash'></span></a></td>";
				html += "<tr>";
			}
			
			$("#tb").append(html);
		} else {
			layer.alert('数据获取失败!', {icon: 2})
		}
	});
}
function addDir(id) {
	var url = "addDirPage?parentId=" + $("input[name='parentId']").val();
	if (id != null && id > 0) {
		url += "&id=" + id;
	}
	layer.open({
		content : url, // iframe的url
		type : 2,
		title : '添加/修改',
		shade : 0.8,
		area : [ '40%', '40%' ],
		btn : [ '确定', '取消' ],
		yes : function(index, layero) {
			var iframeWin = window[layero.find('iframe')[0]['name']];
			iframeWin.saveDir(index);
		},
		cancel : function(index) {
		}
	});
}

function delDir(id) {
	layer.open({
	    content: '你是确认删除？',
	    btn: ['确认', '取消'],
	    shadeClose: false,
	    yes: function(){
	    	parent.layer.open({content: '你点了确认', time: 1});
	        var url = 'deleteById';
			$.post(url,{id:id},function(data){
				if (data.head.resultCode == 200) {
					if(data.body.state==1){
						layer.msg("删除成功!");
						setTimeout(function () {
							list();
						}, 1500);
					}else{
						layer.msg("删除失败!");
					}
				}else {
					layer.alert('数据获取失败!', {icon: 2})
				}
			},'json');
	    }
	});
}
function uploadFile(file,fn) {
	(function(file) {
		var xhr = new XMLHttpRequest();
		if (xhr.upload) {
			// 文件上传成功或是失败
			xhr.onreadystatechange = function(e) {
				if (xhr.readyState == 4) {
					if (xhr.status == 200) {
						fn(xhr);
					} else {
						layer.alert('上传失败！', {icon: 2})
						return false;
					}
				}
			};
			// 开始上传
			var url = "/views/sysFile/uploadFile?name="+encodeURIComponent(file.name)+"&size="+file.size+"&type="+file.type+"&dirId="+$("input[name='parentId']").val();
			xhr.open("POST", url, true);
			xhr.send(file);
		}	
	})(file);	
}

function delFile(id){
	layer.open({
	    content: '你是确认删除？',
	    btn: ['确认', '取消'],
	    shadeClose: false,
	    yes: function(){
	    	parent.layer.open({content: '你点了确认', time: 1});
	        var url = '/views/sysFile/deleteById';
			$.post(url,{id:id},function(data){
				if (data.head.resultCode == 200) {
					if(data.body.state==1){
						layer.msg("删除成功!");
						setTimeout(function () {
							list();
						}, 1500);
					}else{
						layer.msg("删除失败!");
					}
				}else {
					layer.alert('数据获取失败!', {icon: 2})
				}
			},'json');
	    }
	});
}

function viewFile(id,fileName){
	layer.open({
		content : "/views/sysFile/view/"+id, // iframe的url
		type : 2,
		title : fileName,
		offset: 'rb',
		shade: 0 ,
		maxmin: true, //开启最大化最小化按钮
		area : [ '70%', '80%' ],
		cancel : function(index) {
		}
	});
}

function layerMarginAlert(){
	//边缘弹出
	layer.open({
	  type: 1
	  ,offset: 'rb' //具体配置参考：offset参数项
	  ,content: '<div style="padding: 20px 80px;">内容</div>'
	  ,shade: 0 //不显示遮罩
	});
}

function playAudio(fileName,src){
	layer.open({
		title:fileName,  
		type: 1,
		  offset: 'rb', //具体配置参考：offset参数项
		  content: '<div style="padding: 20px 20px;"><audio src="'+src+'" autoplay="autoplay" loop="loop" controls="controls"></audio></div>',
		  shade: 0 //不显示遮罩
		});
}
function playMp4(fileName,src){
	$("#videoTitle").text(fileName);
	html='<video src="'+src+'" autoplay="autoplay" width="540" loop="loop" webkit-playsinline controls="controls"></video>';
	$("#videoBody").append(html);
	$("#INDEXT_RB_AD").show();
}
function closeVideo(){
	$("#INDEXT_RB_AD").hide();
	$("#videoBody").empty();
}
