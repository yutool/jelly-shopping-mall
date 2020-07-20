package com.ankoye.jelly.search.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.ankoye.jelly.goods.domain.Sku;
import com.ankoye.jelly.goods.reference.SkuReference;
import com.ankoye.jelly.search.dao.ESManagerMapper;
import com.ankoye.jelly.search.domain.SkuInfo;
import com.ankoye.jelly.search.service.ESManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author ankoye@qq.com
 */
@Component
public class ESManagerServiceImpl implements ESManagerService {
    @Reference
    private SkuReference skuService;

    @Autowired
    private ESManagerMapper esManagerMapper;

    @Override
    public void importData() {
        // 查询sku
        List<Sku> skuList = skuService.findList();
        // 转换成skuInfo
        List<SkuInfo> skuInfoList = JSON.parseArray(JSON.toJSONString(skuList), SkuInfo.class);
        // 抽出规格
        for (SkuInfo skuInfo : skuInfoList) {
            Map<String, Object> map = JSON.parseObject(skuInfo.getSpec(), Map.class);
            skuInfo.setSpecMap(map);
        }
        // 导入es
        esManagerMapper.saveAll(skuInfoList);
    }
}
