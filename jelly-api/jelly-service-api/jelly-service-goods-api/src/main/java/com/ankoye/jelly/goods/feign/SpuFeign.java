package com.ankoye.jelly.goods.feign;

import com.ankoye.jelly.base.result.Wrapper;
import com.ankoye.jelly.goods.domain.Spu;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author ankoye@qq.com
 */
@FeignClient(value = "jelly-goods-serve", contextId = "spu")
public interface SpuFeign {

    @GetMapping("/spu/{id}")
    Wrapper<Spu> getSpuById(@PathVariable("id") String id);

}
