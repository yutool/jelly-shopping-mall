package com.ankoye.jelly.common.constant;

/**
 * 秒杀 Redis Key
 * @author ankoye@qq.com
 */
public class SeckillKey {

    /**
     * append key: date
     * 秒杀商品
     */
    public static final String GOODS_PRE = "seckill_goods_";

    /**
     * append key: skuId
     * 秒杀商品数量
     */
    public static final String SKU_COUNT_PRE = "seckill_sku_count_";

    /**
     * key: userId + skuId
     * 用户排队信息
     */
    public static final String USER_QUEUE = "seckill_user_queue";

    /**
     * key: orderId
     * 秒杀订单，供用户
     */
    public static final String PREPARE_ORDER = "prepare_order";
}
