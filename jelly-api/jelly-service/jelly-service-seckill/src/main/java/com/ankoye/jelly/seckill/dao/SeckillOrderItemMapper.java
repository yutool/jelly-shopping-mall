package com.ankoye.jelly.seckill.dao;

import com.ankoye.jelly.order.domian.OrderItem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author ankoye@qq.com
 */
@Mapper
public interface SeckillOrderItemMapper extends BaseMapper<OrderItem> {
}
