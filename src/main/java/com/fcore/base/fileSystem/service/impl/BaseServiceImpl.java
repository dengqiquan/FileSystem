package com.fcore.base.fileSystem.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.fcore.base.fileSystem.dao.BaseDao;
import com.fcore.base.fileSystem.service.BaseService;

public abstract class BaseServiceImpl<T, PK extends Serializable> implements BaseService<T, PK> {

	private BaseDao<T, PK> baseDao;
	
	public void setBaseDao(BaseDao<T, PK> baseDao) {
		this.baseDao = baseDao;
	}
	
	@Override
	public PK add(T t) {
		return baseDao.add(t);
	}

	@Override
	public void update(T t) {
		baseDao.update(t);
	}

	@Override
	public T getById(PK id) {
		return baseDao.getById(id);
	}

	@Override
	public List<T> getByParams(Map<String, Object> params) {
		return baseDao.getByParams(params);
	}

	@Override
	public List<T> getList(T t) {
		return baseDao.getList(t);
	}

	@Override
	public int getCount(T t) {
		return baseDao.getCount(t);
	}

	@Override
	public void setBaseDao() {
		
	}

}
