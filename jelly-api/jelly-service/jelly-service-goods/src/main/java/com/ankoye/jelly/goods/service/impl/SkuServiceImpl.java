package com.ankoye.jelly.goods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.ankoye.jelly.goods.dao.SkuMapper;
import com.ankoye.jelly.goods.domain.Sku;
import com.ankoye.jelly.goods.service.SkuService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Service
@Component
public class SkuServiceImpl implements SkuService {
    @Resource
    private SkuMapper skuMapper;

    @Override
    public Sku getSkuById(String id) {
        return skuMapper.selectById(id);
    }
}
