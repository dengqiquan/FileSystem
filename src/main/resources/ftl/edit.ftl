<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org" xmlns:shiro="http://www.pollix.at/thymeleaf/shiro">
<head>
<meta charset="UTF-8" />
<title>${table.table_description}编辑</title>
<link th:href="@{/webjars/bootstrap/3.2.0/css/bootstrap.min.css}" rel="stylesheet" media="screen"></link>
<link rel="stylesheet" th:href="@{/resource/plugins/position-validate/css/validationEngine.jquery.css}"/>
<script type="text/javascript" th:src="@{/webjars/jquery/2.1.1/jquery.min.js}"></script>
<script type="text/javascript" th:src="@{/webjars/bootstrap/3.2.0/js/bootstrap.min.js}"></script>
<script th:src="@{/resource/plugins/position-validate/js/jquery.validationEngine-zh_CN.js}"></script>
<script th:src="@{/resource/plugins/position-validate/js/jquery.validationEngine.js}"></script>
<script type="text/javascript" th:src="@{/resource/plugins/layer/layer.js}"></script>
<script type="text/javascript" th:src="@{/resource/js/edit.js}"></script>
<style type="text/css">
html { overflow-x: hidden; overflow-y: auto; }
</style>
</head>
<body>
<div class="container-fluid" style="padding: 30px;">
	<div class="row">
		<form data="editForm" class="form-horizontal" id="saveObjForm">
			<input type="hidden"  name="${table.primary_colmun?if_exists}" th:value="${r"${"}${table.entityName?uncap_first}.${table.primary_colmun?if_exists}}"/>
    		<table class="table table-bordered" >
     		<#list table.fieldNames as i>
 			<#--排除主键生成form字段-->
    		<#if table.primary_colmun != table.fieldNames[i_index]>
			<tr>
	    		<td  class="text-r" style="width: 15%">${table.remaks[i_index]}</td>
	   			<#if table.fieldNames[i_index] != 'createdate' || table.fieldNames[i_index] != 'changedate'>
	    		<td>
	    			<#if table.filedTypes[i_index] != "Date" && table.colSizes[i_index] &lt; 10000>
		     		<input type="text" style="width:20%" name="${table.fieldNames[i_index]}"  placeholder="请输入${table.remaks[i_index]}" th:value="${r"${"}${table.entityName?uncap_first}.${table.fieldNames[i_index]}}" class="form-control validate[required]"/>
		     		</#if>
		     		<#if table.filedTypes[i_index] == "Date">
		     		<input type="text" style="width:20%;height:33px;" onClick="WdatePicker()"  name="${table.fieldNames[i_index]}"  placeholder="日期" th:value="<fmt:formatDate value="${r"${"}${table.entityName?uncap_first}.${table.fieldNames[i_index]}}" pattern="yyyy-MM-dd HH:mm:ss"/>" class="form-control Wdate validate[required]"/>
		     		</#if>
		     		<#if table.colSizes[i_index] &gt; 10000>
		     		<textarea id="xh_editor" name="${table.fieldNames[i_index]}" cols="100" rows="8" style="width:98%;height:300px;visibility:hidden;">${r"${"}${table.entityName?uncap_first}.${table.fieldNames[i_index]}}</textarea>
		     		</#if>
	    		</td>
	       		</#if>
	    	</tr>
      		</#if>
	</#list>
    </table>
   </form>
</div>
</div>
</body>
</html>