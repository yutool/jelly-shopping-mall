package com.ankoye.jelly.util;

import cn.hutool.core.util.IdUtil;

public class IdUtils {

    public static String getSpuId() {
        return MathUtils.makeUpNewData(Thread.currentThread().hashCode()+"", 3)+ MathUtils.randomDigitNumber(7);
    }

    public static String getSkuId() {
        return IdUtil.objectId();
    }

    public static String getOrderId(long workerId, long dataCenterId) {
        return IdUtil.createSnowflake(workerId, dataCenterId).nextId() + "";
    }

    public static String getOrderId() {
        return getOrderId(0, 1);
    }

    public static String getOrderSn() {
        return "sn" + MathUtils.makeUpNewData(Thread.currentThread().hashCode()+"", 3)+ MathUtils.randomDigitNumber(7);
    }
}
