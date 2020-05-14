package com.ankoye.jelly.seckill.common.constant;

public class RedisKey {

    /**
     * 秒杀商品
     */
    public static final String SECKILL_GOODS = "seckill_goods_";

    /**
     * 秒杀商品数量
     */
    public static final String SECKILL_SKU_COUNT_KEY = "seckill_sku_count_";

    /**
     * 用户排队信息
     */
    public static final String SECKILL_USER_QUEUE = "seckill_user_queue";

    /**
     * 秒杀订单，供用户
     */
    public static final String PREPARE_ORDER = "prepare_order";
}
