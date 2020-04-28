package com.ankoye.jelly.order.service;

import com.ankoye.jelly.order.domian.Order;
import com.ankoye.jelly.order.model.OrderDto;

public interface OrderService {
    /**
     * 查询订单信息
     */
    Order getOrderById(String id);

    /**
     * 创建订单
     * @return 订单编号
     */
    String createOrder(OrderDto orderDto);

    /**
     * 支付成功更改订单状态
     */
    int updateStatus(String id, String payTime, String transactionId);

    /**
     * 订单支付失败
     */
    int payFailStatus(String id);

    /**
     * 超时未支付，删除订单，解冻库存
     */
    int deleteOrder(String id);
}
