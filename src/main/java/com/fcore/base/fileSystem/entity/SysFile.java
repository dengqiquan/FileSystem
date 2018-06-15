package com.fcore.base.fileSystem.entity;

import java.io.Serializable;

/**
* @TableName: sys_file 
* @Package: com.fcore.base.fileSystem.entity
* @Title:SysFile.java 
* @Description:  
* @author: zhangjukai
* @date: 2016-12-22 17:35:58
* @version V1.0    
* create by codeFactory
*/
public class SysFile extends BaseEntity implements Serializable{
	/**
	*@Fields name :文件名
	*/
	private String name;
	/**
	*@Fields size :文件大小
	*/
	private String size;
	/**
	*@Fields type :文件类型
	*/
	private String type;
	/**
	*@Fields suffix :后缀名
	*/
	private String suffix;
	/**
	*@Fields path :文件路径
	*/
	private String path;
	/**
	*@Fields dirId :目录ID
	*/
	private Long dirId;
	/**
	*@Fields createUserName :创建人
	*/
	private String createUserName;
	/**
	*@Fields updateUserName :修改人
	*/
	private String updateUserName;
	private String previewPath;
	
	public String getPreviewPath() {
		return previewPath;
	}

	public void setPreviewPath(String previewPath) {
		this.previewPath = previewPath;
	}

	public void setName(String name){
		this.name=name;
	}
	
	public String getName(){
		return name;
	}
		public void setSize(String size){
		this.size=size;
	}
	
	public String getSize(){
		return size;
	}
		public void setType(String type){
		this.type=type;
	}
	
	public String getType(){
		return type;
	}
		public void setSuffix(String suffix){
		this.suffix=suffix;
	}
	
	public String getSuffix(){
		return suffix;
	}
		public void setPath(String path){
		this.path=path;
	}
	
	public String getPath(){
		return path;
	}
		public void setDirId(Long dirId){
		this.dirId=dirId;
	}
	
	public Long getDirId(){
		return dirId;
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

}

