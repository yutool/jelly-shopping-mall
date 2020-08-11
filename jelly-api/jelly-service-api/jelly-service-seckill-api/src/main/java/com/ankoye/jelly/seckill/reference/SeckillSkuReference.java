package com.ankoye.jelly.seckill.reference;

import com.ankoye.jelly.order.model.OrderModel;

/**
 * @author ankoye@qq.com
 */
public interface SeckillSkuReference {

    Integer updateStock(String id, Integer num);

    /**
     * 回滚库存，删除排队状态
     */
    void rollback(OrderModel orderModel);
}
