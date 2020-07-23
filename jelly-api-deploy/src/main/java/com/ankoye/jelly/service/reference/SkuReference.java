package com.ankoye.jelly.service.reference;


import com.ankoye.jelly.common.support.IService;
import com.ankoye.jelly.domain.Sku;

import java.util.List;

/**
 * @author ankoye@qq.com
 */
public interface SkuReference extends IService<Sku> {

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

    /**
     * 下单成功调用
     */
    boolean paySuccess(String spuId, String skuId, Integer num);

    /**
     * test
     */
    List<Sku> findList();

}
