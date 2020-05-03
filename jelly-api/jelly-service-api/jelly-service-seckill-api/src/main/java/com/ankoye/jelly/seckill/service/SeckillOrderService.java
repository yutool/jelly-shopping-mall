package com.ankoye.jelly.seckill.service;

import com.ankoye.jelly.seckill.domain.SeckillOrder;
import com.ankoye.jelly.seckill.model.OrderQueue;

public interface SeckillOrderService {
    /**
     * 开始排队
     */
    boolean queueUp(String userId, String time, String skuId);

    /**
     * 查询排队状态
     */
    OrderQueue query(String username, String goodsId);

    /**
     * 创建订单
     */
    void create(SeckillOrder seckillOrder);
}
