package com.ankoye.jelly.goods.feign;

import com.ankoye.jelly.base.result.Wrapper;
import com.ankoye.jelly.goods.domain.Sku;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 暂为测试
 * @author ankoye@qq.com
 */
@FeignClient("jelly-goods-serve")
@Component
public interface SkuFeign {

    @GetMapping("/test/sku/{id}")
    Wrapper<Sku> getSkuById(@PathVariable String id);
}
