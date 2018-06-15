package com.fcore.base.fileSystem.entity;

import java.io.Serializable;
import java.util.List;

public class SysUser extends BaseEntity implements Serializable {
	/**
	*@Fields userName :用户名称
	*/
	private String userName;
	/**
	*@Fields loginName :登录名称
	*/
	private String loginName;
	/**
	 * 手机号
	 */
	private String mobile;
	/**
	 * 加密盐
	 */
	private String salt;
	/**
	*@Fields password :登录密码
	*/
	private String password;
	/**
	*@Fields lastLoginIp :最后登录IP
	*/
	private String lastLoginIp;
	/**
	*@Fields lastLoginTime :最后登录时间
	*/
	private String lastLoginTime;
	
	//*************************非数据库字段*******************************//
	
	private String createUserName;
	private String updateUserName;
	private String roleIds;
	
	
	/**
	 * json数组
	 */
	private String allMenu;


	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	public String getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(String lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}


	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public String getUpdateUserName() {
		return updateUserName;
	}

	public void setUpdateUserName(String updateUserName) {
		this.updateUserName = updateUserName;
	}

	public String getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}

	public String getAllMenu() {
		return allMenu;
	}

	public void setAllMenu(String allMenu) {
		this.allMenu = allMenu;
	}
	
}

