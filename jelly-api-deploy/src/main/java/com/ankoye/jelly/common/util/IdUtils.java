package com.ankoye.jelly.common.util;

import cn.hutool.core.util.IdUtil;

/**
 * @author ankoye@qq.com
 */
public class IdUtils {

    /**
     * 获取用户ID
     */
    public static String getUserId() {
        return MathUtils.makeUpNewData(Thread.currentThread().hashCode()+"", 3)+ MathUtils.randomDigitNumber(6);
    }

    /**
     * 获取商品spu ID
     */
    public static String getSpuId() {
        return MathUtils.makeUpNewData(Thread.currentThread().hashCode()+"", 3)+ MathUtils.randomDigitNumber(7);
    }

    /**
     * 获取商品sku ID
     */
    public static String getSkuId() {
        return IdUtil.objectId();
    }

    /**
     * 获取订单ID
     */
    public static String getOrderId(long workerId, long dataCenterId) {
        return IdUtil.createSnowflake(workerId, dataCenterId).nextId() + "";
    }
    public static String getOrderId() {
        return getOrderId(0, 1);
    }

    /**
     * 获取订单编号
     */
    public static String getOrderSn() {
        return "sn" + MathUtils.makeUpNewData(Thread.currentThread().hashCode()+"", 3)+ MathUtils.randomDigitNumber(7);
    }
}
