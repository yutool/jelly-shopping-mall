package com.ankoye.jelly.goods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.ankoye.jelly.goods.dao.SkuMapper;
import com.ankoye.jelly.goods.dao.SpuMapper;
import com.ankoye.jelly.goods.domain.Sku;
import com.ankoye.jelly.goods.service.SkuService;
import org.dromara.hmily.annotation.Hmily;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Component
public class SkuServiceImpl implements SkuService {
    @Resource
    private SkuMapper skuMapper;
    @Autowired
    private SpuMapper spuMapper;

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
    @Transactional
    public boolean paySuccess(String spuId, String skuId, Integer num) {
        skuMapper.decreaseScore(skuId, num);
        skuMapper.addSaleNum(skuId, num);
        spuMapper.addSaleNum(spuId, num);
        return true;
    }

    @Override
    public List<Sku> findList() {
        return null;
    }

    @Override
    @Hmily(confirmMethod = "confirmNested", cancelMethod = "cancelNested")
    public Sku abc() {
        System.out.println("try abc");
        //CastException.cast("a");
        return skuMapper.selectById("5ea294f9b7405bdb68ab9c0a");
    }

    public void confirmNested() {
        System.out.println("abc confirmNested");
    }

    public void cancelNested() {
        System.out.println("abc cancelNested");
    }

}
