package com.ankoye.jelly.order.dao;

import com.ankoye.jelly.order.domian.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author ankoye@qq.com
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {
}
