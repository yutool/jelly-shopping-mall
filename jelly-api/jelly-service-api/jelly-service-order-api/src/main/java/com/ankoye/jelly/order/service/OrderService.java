package com.ankoye.jelly.order.service;

import com.ankoye.jelly.order.domian.Order;
import com.ankoye.jelly.order.model.OrderModel;
import com.ankoye.jelly.web.support.IService;
import com.github.pagehelper.PageInfo;

/**
 * @author ankoye@qq.com
 */
public interface OrderService extends IService<Order> {
    /**
     * 查询订单信息
     */
    OrderModel getOrderById(String id);


    /**
     * 预创建订单 - 临时策略
     */
    String prepare(OrderModel form);

    /**
     * 获取预订单
     */
    OrderModel getPrepareOrder(String id);

    /**
     * 检查预创建的订单
     * 如过预订单存在，说明没创建订单
     * @param id
     */
    void checkPrepareOrder(String id);

    /**
     * 创建订单
     * @param form 包含住址等信息
     */
    String create(OrderModel form);

    /**
     * 支付成功更改订单状态
     */
    boolean updateStatus(String id, String payTime, String transactionId);

    /**
     * 订单支付失败
     */
    int payFailStatus(String id);

    /**
     * 回查订单
     */
    int checkOrder(String id);

    /**
     * 获取用户所有订单
     * @param id 用户id
     */
    PageInfo<OrderModel> getByUserId(String id, Integer page, Integer size);

    /**
     * 删除订单
     * @param id 订单id
     */
    int deleteById(String id);

}
