package com.ankoye.jelly.dao;

import com.ankoye.jelly.domain.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author ankoye@qq.com
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {
}
