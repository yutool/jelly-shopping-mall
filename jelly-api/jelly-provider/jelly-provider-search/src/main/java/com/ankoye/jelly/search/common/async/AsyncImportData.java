package com.ankoye.jelly.search.common.async;

import com.alibaba.fastjson.JSON;
import com.ankoye.jelly.goods.domain.Sku;
import com.ankoye.jelly.goods.feign.SkuFeign;
import com.ankoye.jelly.search.dao.ESManagerMapper;
import com.ankoye.jelly.search.domain.SkuInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author ankoye@qq.com
 */
@Component
public class AsyncImportData {
    @Autowired
    private SkuFeign skuFeign;

    @Autowired
    private ESManagerMapper esManagerMapper;

    @Async
    public void importSku() {
        // 查询sku
        List<Sku> skuList = skuFeign.getAll().getData();
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
