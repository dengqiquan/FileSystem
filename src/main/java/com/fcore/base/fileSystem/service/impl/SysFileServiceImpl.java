package com.fcore.base.fileSystem.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fcore.base.fileSystem.dao.SysFileDao;
import com.fcore.base.fileSystem.entity.SysFile;
import com.fcore.base.fileSystem.service.SysFileService;

 /**   
* @Title: SysFileServiceImpl.java 
* @Package com.fcore.base.fileSystem.service
* @Description: 
* @author zhangjukai
* @date 2016-12-22 17:35:59
* @version V1.0   
* create by codeFactory
*/
@Service("SysFileServiceImpl")
public class SysFileServiceImpl  extends BaseServiceImpl<SysFile,Long> implements SysFileService{
	@Autowired
	private SysFileDao sysFileDao;
	@Autowired
	public void setBaseDao() {
		super.setBaseDao(sysFileDao);
	}
}