package com.mdd.admin.mongo.service;

import com.mdd.common.core.PageResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;


public interface BaseMongoService<T> {
	
	/**
	 * 对于id在数据库中存在的数据， 要先getById(id)再更新字段，否则部分不希望更新的字段会被覆盖掉；<br/>
	 * 如id不存在，则直接保存
	 * @return
	 * 2019年5月8日下午5:59:18
	 */
	T save(T entity);

	/**
	 * 对非String 型的空数据会有影响，因为会有默认值
	 * @param entity 更新对象
	 * @return 更新后的对象
	 */
	T update(T entity);

	void delete(Object[] ids);
	
	void delete(Object id);
	
	T getById(Object id);

	List<T> findAll();

	List<T> findAll(String order);

	List<T> findByProp(String propName, Object value);

	List<T> findByProp(String propName, Object value, String order);

	/**
	 * 注意 includeFields 和 excludeFields 不能同时使用
	 * @param propName		查询自字段名
	 * @param value			查询字段值
	 * @param includeFields 查询结果包含项
	 * @param excludeFields 查询结果排除项
	 * @return
	 */
	List<T> findByProp(String propName, Object value, String[] includeFields, String[] excludeFields);

	List<T> findByProps(String[] propName, Object[] values);

	List<T> findByProps(String[] propName, Object[] values, String order);

	/**
	 * 注意 includeFields 和 excludeFields 不能同时使用
	 * @param propName		查询字段组合
	 * @param values		查询值组合，一一对应
	 * @param includeFields	查询结果中包含的字段
	 * @param excludeFields	查询结果中不包含字段
	 * @return				查询结果list
	 */
	List<T> findByProps(String[] propName, Object[] values, String[] includeFields, String[] excludeFields);

	T uniqueByProp(String propName, Object value);

	T uniqueByProps(String[] propName, Object[] values);

	long countByCondition(String[] params, Object[] values);

	Page<T> findPage(PageRequest pageReq);
	
	Page<T> findPage(PageRequest pageReq, String propName, Object value);
	
	Page<T> findPage(PageRequest pageReq, String[] propName, Object[] values, String order);
	
	Page<T> findPage(PageRequest pageReq, String propName, Object value, String[] includeFields, String[] excludeFields);
	
	Page<T> findPage(PageRequest pageReq, String[] propName, Object[] values, String[] includeFields, String[] excludeFields);

	PageResult<T> findPage(PageRequest pageReq, Object queryFilter);

	PageResult<T> findPage(PageRequest pageReq, Object queryFilter, String[] includeFields, String[] excludeFields);
}
