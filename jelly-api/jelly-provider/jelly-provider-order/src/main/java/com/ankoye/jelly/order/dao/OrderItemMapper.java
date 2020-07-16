package com.ankoye.jelly.order.dao;

import com.ankoye.jelly.order.domian.OrderItem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author ankoye@qq.com
 */
@Mapper
public interface OrderItemMapper extends BaseMapper<OrderItem> {
}
