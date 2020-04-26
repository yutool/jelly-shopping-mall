package com.ankoye.jelly.order.service;

import com.ankoye.jelly.order.model.OrderDto;

public interface OrderService {
    /**
     * 创建订单
     * @return 订单编号
     */
    String createOrder(OrderDto orderDto);
}
