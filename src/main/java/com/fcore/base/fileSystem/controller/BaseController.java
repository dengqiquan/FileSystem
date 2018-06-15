package com.fcore.base.fileSystem.controller;

import com.fcore.base.fileSystem.entity.SysUser;

public class BaseController {
	public  SysUser getSessionUser(){
		SysUser user = new SysUser();
		user.setId(1l);
		user.setUserName("zjk");
		return user;
	}
}
