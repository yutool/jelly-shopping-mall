package com.ankoye.jelly.seckill.service;

import com.ankoye.jelly.order.domian.Order;
import com.ankoye.jelly.order.model.OrderModel;
import com.ankoye.jelly.seckill.domain.SeckillOrder;
import com.ankoye.jelly.seckill.domain.SeckillSku;
import com.ankoye.jelly.seckill.model.OrderQueue;
import com.ankoye.jelly.web.support.IService;

/**
 * @author ankoye@qq.com
 */
public interface SeckillOrderService  {
    /**
     * 开始排队
     */
    boolean queueUp(OrderQueue orderQueue);

    /**
     * 查询排队状态
     */
    OrderQueue queryQueue(String username, String goodsId);

    /**
     * 预创建订单
     */
    String prepare(String userId, SeckillSku sk);

    /**
     * 回滚库存，删除排队状态
     */
    void rollback(OrderModel orderModel);

    /**
     * 回查秒杀订单
     * @param orderId
     * @return
     */
    int checkOrder(String orderId);
}
