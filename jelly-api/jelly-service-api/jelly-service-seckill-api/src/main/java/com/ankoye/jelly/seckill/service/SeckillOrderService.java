package com.ankoye.jelly.seckill.service;

import com.ankoye.jelly.seckill.domain.SeckillSku;
import com.ankoye.jelly.seckill.model.OrderQueue;

public interface SeckillOrderService {
    /**
     * 开始排队
     */
    boolean queueUp(OrderQueue orderQueue);

    /**
     * 查询排队状态
     */
    OrderQueue query(String username, String goodsId);

    /**
     * 创建订单
     */
    String create(String userId, SeckillSku sk);
}
