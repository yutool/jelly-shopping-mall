package com.ankoye.jelly.order.service;

import com.ankoye.jelly.order.model.OrderDto;

import java.util.List;

public interface OrderService {
    /**
     * 查询订单信息
     */
    OrderDto getOrderById(String id);

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

    /**
     * 获取用户所有订单
     * @param id 用户id
     */
    List<OrderDto> getByUserId(String id);

    /**
     * 删除订单
     * @param id 订单id
     */
    int deleteById(String id);

    //@Hmily
    void test();
}
