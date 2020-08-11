package com.ankoye.jelly.seckill.service;

import com.ankoye.jelly.order.model.OrderModel;
import com.ankoye.jelly.seckill.domain.SeckillSku;
import com.ankoye.jelly.seckill.model.OrderQueue;

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
     * 回查秒杀订单
     * @param orderId
     * @return
     */
    boolean checkOrder(String orderId);
}
