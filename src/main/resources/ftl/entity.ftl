package ${base_packge}.${type_model};

import java.io.Serializable;
<#if table.f_util == true>
import java.util.*;
</#if>
<#if table.f_sql == true>
import java.sql.*;
</#if>
<#if table.f_math == true>
import import java.math.*;
</#if>

/**
* @TableName: ${table.tablename} 
* @Package: ${base_packge}.${type_model}
* @Title:${table.entityName}.java 
* @Description: ${table.table_description} 
* @author: ${author}
* @date: ${current_now}
* @version V1.0    
* create by codeFactory
*/
public class ${table.entityName} extends BaseEntity implements Serializable{
	<#list table.fieldNames as field>
	<#if !"id,createUserId,createTime,updateUserId,updateTime,isDelete"?contains(field)>
	/**
	*@Fields ${field} :${table.remaks[field_index]}
	*/
	private ${table.filedTypes[field_index]} ${field};
	</#if>
	</#list>
	<#list table.fieldNames as field>
	<#if !"id,createUserId,createTime,updateUserId,updateTime,isDelete"?contains(field)>
		public void set${field?cap_first}(${table.filedTypes[field_index]} ${field?uncap_first}){
		this.${field?uncap_first}=${field?uncap_first};
	}
	
	public ${table.filedTypes[field_index]} get${field?cap_first}(){
		return ${field?uncap_first};
	}
	</#if>
	</#list>
}

