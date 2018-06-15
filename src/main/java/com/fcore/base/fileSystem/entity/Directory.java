package com.fcore.base.fileSystem.entity;

import java.io.Serializable;

/**
* @TableName: directory 
* @Package: com.fcore.base.fileSystem.entity
* @Title:Directory.java 
* @Description:  
* @author: zhangjukai
* @date: 2016-12-21 14:33:04
* @version V1.0    
* create by codeFactory
*/
public class Directory extends BaseEntity implements Serializable{
	/**
	*@Fields name :目录名称
	*/
	private String name;
	/**
	*@Fields parentId :父目录ID
	*/
	private Long parentId;
	/**
	*@Fields createUserName :创建人
	*/
	private String createUserName;
	/**
	*@Fields updateUserName :修改人
	*/
	private String updateUserName;
	/**
	*@Fields curPath :当前路径
	*/
	private String curPath;
	/**
	*@Fields idDelete :是否删除
	*/
	private Integer idDelete;
		public void setName(String name){
		this.name=name;
	}
	
	public String getName(){
		return name;
	}
		public void setParentId(Long parentId){
		this.parentId=parentId;
	}
	
	public Long getParentId(){
		return parentId;
	}
		public void setCreateUserName(String createUserName){
		this.createUserName=createUserName;
	}
	
	public String getCreateUserName(){
		return createUserName;
	}
		public void setUpdateUserName(String updateUserName){
		this.updateUserName=updateUserName;
	}
	
	public String getUpdateUserName(){
		return updateUserName;
	}
		public void setCurPath(String curPath){
		this.curPath=curPath;
	}
	
	public String getCurPath(){
		return curPath;
	}
		public void setIdDelete(Integer idDelete){
		this.idDelete=idDelete;
	}
	
	public Integer getIdDelete(){
		return idDelete;
	}
}

