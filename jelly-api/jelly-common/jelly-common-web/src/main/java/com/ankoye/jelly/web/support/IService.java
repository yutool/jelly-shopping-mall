/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：IService.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.ankoye.jelly.web.support;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * 通用接口
 *
 * @param <T> the type parameter @author paascloud.net@gmail.com
 *
 * @author ankoye@qq.com
 */
public interface IService<T> {
	/**
	 * 根据实体中的属性值进行查询, 查询条件使用等号  @param record the record
	 *
	 * @param record the record
	 *
	 * @return the list
	 */
	List<T> selectList(T record);

	List<T> selectList(Wrapper<T> wrapper);

	/**
	 * 根据主键字段进行查询, 方法参数必须包含完整的主键属性, 查询条件使用等号  @param key the key
	 *
	 * @param id the key
	 *
	 * @return the t
	 */
	T selectById(Object id);

	/**
	 * 查询全部结果, select(null)方法能达到同样的效果  @return the list
	 *
	 * @return the list
	 */
	List<T> selectAll();

	/**
	 * 根据实体中的属性进行查询, 只能有一个返回值, 有多个结果是抛出异常, 查询条件使用等号  @param record the record
	 *
	 * @param record the record
	 *
	 * @return the t
	 */
	T selectOne(T record);

	/**
	 * 根据实体中的属性查询总数, 查询条件使用等号  @param record the record
	 *
	 * @param record the record
	 *
	 * @return the int
	 */
	int selectCount(T record);

	/**
	 * 根据实体属性和RowBounds进行分页查询  @param record the record
	 *
	 * @param record    the record
	 * @param page the row bounds
	 * @param size
	 *
	 * @return the list
	 */
	List<T> selectByRowBounds(T record, Integer page, Integer size);

	/**
	 * 保存一个实体, null的属性不会保存, 会使用数据库默认值  @param record the record
	 *
	 * @param record the record
	 *
	 * @return the int
	 */
	int save(T record);

	/**
	 * 批量保存  @param list the list
	 *
	 * @param list the list
	 *
	 * @return the int
	 */
	@Transactional(rollbackFor = Exception.class)
	int batchSave(List<T> list);

	/**
	 * 根据主键更新属性不为null的值  @param entity the entity
	 *
	 * @param entity the entity
	 *
	 * @return the int
	 */
	int updateById(T entity);

	/**
	 * 根据实体属性作为条件进行删除, 查询条件使用等号  @param record the record
	 *
	 * @param record the record
	 *
	 * @return the int
	 */
	int delete(T record);

	/**
	 * 批量删除  @param list the list
	 *
	 * @param list the list
	 *
	 * @return the int
	 */
	@Transactional(rollbackFor = Exception.class)
	int batchDelete(List<T> list);

	/**
	 * 根据主键字段进行删除, 方法参数必须包含完整的主键属性  @param key the key
	 *
	 * @param id the key
	 *
	 * @return the int
	 */
	int deleteById(Object id);

	/**
	 * 根据Example条件删除数据  @param example the example
	 *
	 * @param ids the id list
	 *
	 * @return the int
	 */
	int batchDeleteById(List<Object> ids);

}
