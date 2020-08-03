package com.ankoye.jelly.goods.feign.fallback;

import com.ankoye.jelly.base.result.Wrapper;
import com.ankoye.jelly.goods.domain.Sku;
import com.ankoye.jelly.goods.feign.SkuFeign;

import java.util.List;

public class SkuFeignFallback implements SkuFeign {
    @Override
    public Wrapper<Sku> getById(String id) {
        return null;
    }

    @Override
    public Wrapper<List<Sku>> getAll() {
        return null;
    }

    @Override
    public Wrapper freezeScore(String skuId, Integer num) {
        return null;
    }

    @Override
    public Wrapper paySuccess(String spuId, String skuId, Integer num) {
        return null;
    }

    @Override
    public Wrapper unfreezeScore(String skuId, Integer num) {
        return null;
    }
}
