package com.ankoye.jelly.util;

import cn.hutool.core.util.IdUtil;

public class IdUtils {

    public static String getSpuId() {
        return MathUtils.makeUpNewData(Thread.currentThread().hashCode()+"", 3)+ MathUtils.randomDigitNumber(7);
    }

    public static String getSkuId() {
        return IdUtil.objectId();
    }
}
