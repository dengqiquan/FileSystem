package com.fcore.base.fileSystem.service;

import com.fcore.base.fileSystem.entity.Directory;

public interface DirectoryService extends BaseService<Directory,Long>{

	Boolean checkDirName(Long id, String name, Long parentId);

	String getParentCurPath(String curPath);

}