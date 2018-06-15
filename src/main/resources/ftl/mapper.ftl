<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
"http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${base_packge}.${type_dao}.${table.entityName?cap_first}Dao">
	<resultMap id="result_${table.entityName?cap_first}Map" type="${base_packge}.${type_model}.${table.entityName?cap_first}">
	</resultMap>
	
	<sql id="Base_Column_List">
	<#list table.table_filelds as field>${field}<#if field_index+1 != table.table_filelds?size>,</#if></#list>
	</sql>
	
    <!-- 单条新增  -->
	<insert id="add" parameterType="${base_packge}.${type_model}.${table.entityName?cap_first}">
		<![CDATA[
		INSERT INTO ${table.tablename}(<#list table.table_filelds as field>${field}<#if field_index+1 != table.table_filelds?size>,</#if></#list>) 
		VALUES 
		(<#list table.fieldNames as field>${r'#{'}${field}${r'}'}<#if field_index+1 != table.fieldNames?size>,</#if></#list>)
		]]>
		<selectKey resultType="long" keyProperty="${table.primary_colmun}">
			select @@IDENTITY as ${table.primary_colmun}
		</selectKey>
    </insert>
    
     <!-- 根据id查询 -->
	<select id="getById" parameterType="long" resultMap="result_${table.entityName?cap_first}Map">
		select <include refid="Base_Column_List"/> from ${table.tablename} where ${table.primary_colmun}=${r'#{'}${table.primary_colmun}${r'}'}
	</select>
	
	<!-- 分页查询列表 -->
	<select id="getList"  resultMap="result_${table.entityName?cap_first}Map" parameterType="${base_packge}.${type_model}.${table.entityName?cap_first}">
    	select <include refid="Base_Column_List"/> from ${table.tablename}
    	where isDelete = 1
    	order by ${table.primary_colmun1} desc 
    	<if test="pageSize!=null and pageSize>0 and pageNumber!=null and pageNumber>0">
    		limit ${r"$"}{(pageNumber-1)*pageSize},${r'#'}{pageSize}
    	</if>
    </select>
    <select id="getCount" parameterType="${base_packge}.${type_model}.${table.entityName?cap_first}" resultType="int">
   		 select count(1) from ${table.tablename}
   		 where isDelete = 1
    </select>

	
	<!-- 根据id修改记录 -->
	<update id="update" parameterType="${base_packge}.${type_model}.${table.entityName?cap_first}">
		update ${table.tablename}
		<trim prefix="set" prefixOverrides=",">
		<#list table.fieldNames as field>
			<#if table.table_filelds[field_index] != table.primary_colmun>
			<#if table.filedTypes[field_index] == 'Integer' || table.filedTypes[field_index] == 'Long'>
			<if test="${field} != null and ${field} >0">
			</#if>
			<#if table.filedTypes[field_index] != 'Integer' && table.filedTypes[field_index] != 'Long'>
			<if test="${field} != null and ${field} != ''">
			</#if>
			,${table.table_filelds[field_index]} = ${r'#{'}${field}${r'}'}
			</if>
			</#if>
		</#list>
		</trim>
		where ${table.primary_colmun1}=${r'#{'}${table.primary_colmun}${r'}'}
	</update>
	
	<!-- 根据条件查询 -->
	<select id="getByParams" resultMap="result_${table.entityName?cap_first}Map" parameterType="java.util.Map">
		select <include refid="Base_Column_List"/> from ${table.tablename} where isDelete = 1
		<#list table.fieldNames as field>
			<if test="${field} != null and ${field} != '' ">
		      and ${table.table_filelds[field_index]}=${r'#{'}${field}${r'}'}
	        </if>
		</#list>
	</select>
</mapper>