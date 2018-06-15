package com.fcore.base.fileSystem.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fcore.base.fileSystem.dao.DirectoryDao;
import com.fcore.base.fileSystem.entity.Directory;
import com.fcore.base.fileSystem.service.DirectoryService;

 /**   
* @Title: DirectoryServiceImpl.java 
* @Package com.fcore.base.fileSystem.service
* @Description: 
* @author zhangjukai
* @date 2016-12-21 14:33:04
* @version V1.0   
* create by codeFactory
*/
@Service("DirectoryServiceImpl")
public class DirectoryServiceImpl  extends BaseServiceImpl<Directory,Long> implements DirectoryService{
	@Autowired
	private DirectoryDao directoryDao;
	@Autowired
	public void setBaseDao() {
		super.setBaseDao(directoryDao);
	}
	@Override
	public Boolean checkDirName(Long id,String name, Long parentId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		params.put("name", name);
		params.put("parentId", parentId);
		int count = directoryDao.findForCheckName(params);
		if(count>0){
			return true;
		}
		return false;
	}
	@Override
	public String getParentCurPath(String curPath) {
		curPath = curPath.substring(0,curPath.length()-1);
		return curPath.substring(0,curPath.lastIndexOf("/")+1);
	}
}