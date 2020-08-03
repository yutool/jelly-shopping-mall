/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：BaseService.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.ankoye.jelly.web.support;

import com.ankoye.jelly.web.exception.CastException;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * The class Base service.
 *
 * @param <T> the type parameter
 *
 * @author ankoye@qq.com
 */
public abstract class BaseService<T extends Serializable> implements IService<T> {

	/**
	 * The Logger.
	 */
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * The Mapper.
	 */
	@Autowired
	protected BaseMapper<T> mapper;

	/**
	 * Gets mapper.
	 *
	 * @return the mapper
	 */
	public BaseMapper<T> getMapper() {
		return mapper;
	}

	/**
	 * Select by key t.
	 *
	 * @param id the key
	 *
	 * @return the t
	 */
	@Override
	public T selectById(Object id) {
		return mapper.selectById((Serializable) id);
	}

	/**
	 * Select one t.
	 *
	 * @param record the record
	 *
	 * @return the t
	 */
	@Override
	public T selectOne(T record) {
		return mapper.selectOne(Wrappers.query(record));
	}

	/**
	 * Select list.
	 *
	 * @param record the record
	 *
	 * @return the list
	 */
	@Override
	public List<T> selectList(T record) {
		return mapper.selectList(Wrappers.query(record));
	}

	public List<T> selectList(Wrapper<T> wrapper) {
		return mapper.selectList(wrapper);
	}

	/**
	 * Select all list.
	 *
	 * @return the list
	 */
	@Override
	public List<T> selectAll() {
		return mapper.selectList(null);
	}

	/**
	 * Select count int.
	 *
	 * @param record the record
	 *
	 * @return the int
	 */
	@Override
	public int selectCount(T record) {
		return mapper.selectCount(Wrappers.query(record));
	}

	/**
	 * Select by row bounds list.
	 *
	 * @param record    the record
	 * @param page
	 * @param size
	 * @return the list
	 */
	@Override
	public List<T> selectByRowBounds(T record, Integer page, Integer size) {
		PageHelper.startPage(page, size);
		return mapper.selectList(Wrappers.query(record));
	}

	/**
	 * Save int.
	 *
	 * @param record the record
	 *
	 * @return the int
	 */
	@Override
	public int save(T record) {
		return mapper.insert(record);
	}

	/**
	 * Batch save int.
	 *
	 * @param list the list
	 *
	 * @return the int
	 */
	@Override
	public int batchSave(List<T> list) {
		int result = 0;
		for (T record : list) {
			int count = mapper.insert(record);
			result += count;
		}
		return result;
	}

	/**
	 * Update int.
	 *
	 * @param entity the entity
	 *
	 * @return the int
	 */
	@Override
	public int updateById(T entity) {
		return mapper.updateById(entity);
	}

	/**
	 * Delete int.
	 *
	 * @param record the record
	 *
	 * @return the int
	 */
	@Override
	public int delete(T record) {
		return mapper.delete(Wrappers.update(record));
	}

	/**
	 * Delete by key int.
	 *
	 * @param id the key
	 *
	 * @return the int
	 */
	@Override
	public int deleteById(Object id) {
		return mapper.deleteById((Serializable) id);
	}

	/**
	 * Batch delete int.
	 *
	 * @param list the list
	 *
	 * @return the int
	 */
	@Override
	public int batchDelete(List<T> list) {
		int result = 0;
		for (T record : list) {
			int count = mapper.delete(new QueryWrapper<>(record));
			if (count < 1) {
				logger.error("删除数据失败");
				CastException.cast("删除数据失败");
			}
			result += count;
		}
		return result;
	}

	/**
	 * Delete by example int.
	 *
	 * @param ids the id list
	 *
	 * @return the int
	 */
	@Override
	public int batchDeleteById(List<Object> ids) {
		List<Serializable> id = ids.stream().map(item -> (Serializable)item).collect(Collectors.toList());
		return mapper.deleteBatchIds(id);
	}

}
