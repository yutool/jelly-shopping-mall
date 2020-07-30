package com.ankoye.jelly.goods.feign;

import com.ankoye.jelly.base.result.Wrapper;
import com.ankoye.jelly.goods.domain.Sku;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

/**
 * @author ankoye@qq.com
 */
@FeignClient(value = "jelly-goods-serve", contextId = "sku")
public interface SkuFeign {

    /**
     * 错误总结：
     *
     * 启动报错：Caused by: java.lang.IllegalStateException: PathVariable annotation was empty on param 0.
     * 大概意思是PathVariable注解的第一个参数不能为空
     * 需要给@PathVariable 添加参数
     */

    @GetMapping("/sku/{id}")
    Wrapper<Sku> getSkuById(@PathVariable("id") String id);

    @GetMapping("/sku/all")
    Wrapper<List<Sku>> getAll();

    @PutMapping("/sku/freezeScore/{skuId}/{num}")
    Wrapper freezeScore(@PathVariable("skuId") String skuId, @PathVariable("num") Integer num);

    @PutMapping("/sku/paySuccess/{spuId}/{skuId}/{num}")
    Wrapper paySuccess(@PathVariable("spuId") String spuId, @PathVariable("skuId") String skuId, @PathVariable("num") Integer num);

    @PutMapping("/sku/unfreezeScore/{skuId}/{num}")
    Wrapper unfreezeScore(@PathVariable("skuId") String skuId, @PathVariable("num") Integer num);
}