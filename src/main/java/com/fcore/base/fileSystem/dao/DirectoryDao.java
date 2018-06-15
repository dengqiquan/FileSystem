package com.fcore.base.fileSystem.dao;

import java.util.List;
import java.util.Map;

import com.fcore.base.fileSystem.entity.Directory;

/**   
* @Title: IDirectoryMapper.java 
* @Package com.fcore.base.fileSystem.dao
* @Description: 
* @author zhangjukai
* @date 2016-12-21 14:33:04
* @version V1.0   
* create by codeFactory
*/
public interface DirectoryDao extends BaseDao<Directory,Long>{

	int findForCheckName(Map<String, Object> params);
	
}
