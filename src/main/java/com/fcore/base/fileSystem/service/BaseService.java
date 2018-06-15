package com.fcore.base.fileSystem.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface BaseService<T, PK extends Serializable> {
	void setBaseDao();
	/**
	 * 添加
	 * @param t
	 * @return
	 */
	public PK add(T t);
	/**
	 * 更新
	 * @param t
	 */
	public void update(T t);
	/**
	 * 根据ID查询
	 * @param id
	 * @return
	 */
	public T getById(PK id);
	/**
	 * 根据参数查询一条数据
	 * @param params
	 * @return
	 */
	public List<T> getByParams(Map<String, Object> params);
	
	/**
	 * 根据条件查询列表
	 * @param params
	 * @return
	 */
	public List<T> getList(T t);
	
	/**
	 * 根据条件查询总条数
	 * @param params
	 * @return
	 */
	public int getCount(T t);
	
}
