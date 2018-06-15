<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org" xmlns:shiro="http://www.pollix.at/thymeleaf/shiro">
<head>
<meta charset="UTF-8" />
<title>列表</title>
<link th:href="@{/webjars/bootstrap/3.2.0/css/bootstrap.min.css}" rel="stylesheet" media="screen"></link>
<link th:href="@{/resource/css/simplePagination.css}" rel="stylesheet" media="screen"></link>
<script type="text/javascript" th:src="@{/webjars/jquery/2.1.1/jquery.min.js}"></script>
<script type="text/javascript" th:src="@{/resource/js/jquery.simplePagination.js}"></script>
<script type="text/javascript" th:src="@{/webjars/bootstrap/3.2.0/js/bootstrap.min.js}"></script>
<script type="text/javascript" th:src="@{/resource/plugins/layer/layer.js}"></script>
<script type="text/javascript" th:src="@{/resource/js/list.js}"></script>
<style type="text/css">
th{text-align: center;}
tbody{color: #666;}
html { overflow-x: hidden; overflow-y: auto; }
</style>
</head>
<body>
<div class="container-fluid" style="padding: 30px;">
<form id="searchForm" action="list" method="post" class="form-inline" >
<input type="hidden" value="" th:value="${r"$"}{pager.pageNumber}" name="pageNumber" id="pageNumber"/>
<input type="hidden" value="" th:value="${r"$"}{pager.pageCount}" id="pageCount"/>
<div class="row" style="margin-bottom: 10px;">
	<div class="col-md-12">
		<label></label>
		<input type="text" class="form-control lg-width" placeholder="输入关键字" name="userName" value="" />
		<button type="submit" class="btn btn-info">查询</button>
		<a class="btn btn-success" href="javascript:void(0)" onclick="addOrEdit()">添加</a>
	</div>
</div>
<div class="row">
<table class="table table-striped table-bordered text-center">
	<thead>
        <tr>
		<#list table.fieldNames as i>
		<th>${table.remaks[i_index]}</th>
        </#list>
      	<th>操作</th>
        </tr>
    </thead>
     <tr th:each="obj:${r"$"}{pager.list}">
		<#list table.fieldNames as i>
			<td th:text="${r"$"}{obj.${table.fieldNames[i_index]}}">${table.fieldNames[i_index]}</td>
		</#list>
		 <td >
	  		<a class="btn btn-primary btn-xs"   th:onclick="'addOrEdit(\''+${r"$"}{obj.${table.primary_colmun?if_exists}}+'\')" role="button" title="编辑">编辑</a>
	  	    <a class="btn btn-primary btn-xs" th:onclick="'del(\'' +${r"$"}{obj.${table.primary_colmun} }+ '\')'" href="javascript:del('${r"$"}{obj.${table.primary_colmun} }');" role="button" title="删除">删除</a>
	     </td>
	</tr>
     <tr th:if="${r"$"}{pager.totalCount lt 1}">
		<td colspan="${table.fieldNames?size+1}" class="text-center">暂无数据!!!</td>
	 </tr>
</table>
</div>
</form>
<div style="height: auto;width: 100%;text-align: right;">
		<div class="pagination-holder clearfix" >
	    	<div id="light-pagination" class="pagination" style="margin-right: -23px;"></div>
	    </div>			
	</div>
</div>
</body>
</html>
