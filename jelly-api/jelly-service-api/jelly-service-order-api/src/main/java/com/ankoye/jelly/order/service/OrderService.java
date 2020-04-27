package com.ankoye.jelly.order.service;

import com.ankoye.jelly.order.domian.Order;
import com.ankoye.jelly.order.model.OrderDto;

public interface OrderService {
    /**
     * 获取订单
     */
    Order getOrderById(String id);

    /**
     * 创建订单
     * @return 订单编号
     */
    String createOrder(OrderDto orderDto);
}
