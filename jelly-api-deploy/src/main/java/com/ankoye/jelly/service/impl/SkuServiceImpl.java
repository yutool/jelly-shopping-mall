package com.ankoye.jelly.service.impl;

import com.ankoye.jelly.common.support.BaseService;
import com.ankoye.jelly.dao.SkuMapper;
import com.ankoye.jelly.dao.SpuMapper;
import com.ankoye.jelly.domain.Sku;
import com.ankoye.jelly.service.reference.SkuReference;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ankoye@qq.com
 */
@Service
@Component
public class SkuServiceImpl extends BaseService<Sku> implements SkuReference {
    @Resource
    private SkuMapper skuMapper;
    @Resource
    private SpuMapper spuMapper;

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


    public boolean confirm(String spuId, String skuId, Integer num) {
        System.out.println("商品状态更新成功");
        return true;
    }

    public boolean cancel(String spuId, String skuId, Integer num) {
        System.out.println("商品状态更新失败");
        return false;
    }

}
