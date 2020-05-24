package com.ankoye.jelly.goods.service;

import com.ankoye.jelly.goods.domain.Sku;
import org.dromara.hmily.annotation.Hmily;

import java.util.List;

public interface SkuService {
    /**
     * 获取 商品sku
     */
    Sku getSkuById(String id);

    /**
     * 扣减库存
     * @param num 扣减的数量
     */
    int decreaseScore(String id, Integer num);

    /**
     * 冻结库存
     */
    int freezeScore(String id, Integer num);

    /**
     * 解冻库存
     */
    int unfreezeScore(String id, Integer num);

    @Hmily
    Sku abc();

    List<Sku> findList();
}
