package com.ankoye.jelly.goods.service;

import com.ankoye.jelly.goods.domain.Sku;
import com.ankoye.jelly.web.support.IService;
import org.dromara.hmily.annotation.Hmily;

import java.util.List;

/**
 * @author ankoye@qq.com
 */
public interface SkuService extends IService<Sku> {
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

    /**
     * 下单成功调用
     */
    @Hmily
    boolean paySuccess(String spuId, String skuId, Integer num);

    /**
     * test
     */
    List<Sku> findList();

}