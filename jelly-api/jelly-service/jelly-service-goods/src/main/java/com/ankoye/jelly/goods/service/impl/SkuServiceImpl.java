package com.ankoye.jelly.goods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.ankoye.jelly.goods.dao.SkuMapper;
import com.ankoye.jelly.goods.domain.Sku;
import com.ankoye.jelly.goods.service.SkuService;
import org.dromara.hmily.annotation.Hmily;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Service
@Component
//@Service("skuService")
public class SkuServiceImpl implements SkuService {
    @Resource
    private SkuMapper skuMapper;

    @Override
    public Sku getSkuById(String id) {
        return skuMapper.selectById(id);
    }

    @Override
    public int decreaseScore(String id, Integer num) {
        return skuMapper.decreaseScore(id, num);
    }

    @Override
    public int freezeScore(String id, Integer num) {
        return skuMapper.freezeScore(id, num);
    }

    @Override
    public int unfreezeScore(String id, Integer num) {
        return skuMapper.unfreezeScore(id, num);
    }

    @Override
    @Hmily(confirmMethod = "confirmNested", cancelMethod = "cancelNested")
    public void abc() {
        System.out.println("try abc");
        //CastException.cast("a");
    }

    public void confirmNested() {
        System.out.println("abc confirmNested");
    }

    public void cancelNested() {
        System.out.println("abc cancelNested");
    }

}
