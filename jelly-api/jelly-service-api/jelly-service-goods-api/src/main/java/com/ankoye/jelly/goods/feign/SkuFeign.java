package com.ankoye.jelly.goods.feign;

import com.ankoye.jelly.base.result.RestResult;
import com.ankoye.jelly.goods.domain.Sku;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("jelly-goods-serve")
@Component
public interface SkuFeign {
    @GetMapping("/v1/sku/{id}")
    RestResult<Sku> getSkuById(@PathVariable String id);
}
