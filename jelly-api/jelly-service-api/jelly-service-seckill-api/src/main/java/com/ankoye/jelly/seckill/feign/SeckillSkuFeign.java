package com.ankoye.jelly.seckill.feign;

import com.ankoye.jelly.base.result.Wrapper;
import com.ankoye.jelly.order.model.OrderModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author ankoye@qq.com
 */
@FeignClient(value = "jelly-seckill-serve")
public interface SeckillSkuFeign {

    @PutMapping("/api/seckill/sku/stock/{id}/{num}")
    Wrapper<Integer> updateStock(@PathVariable("id") String id, @PathVariable("num") Integer num);

    @GetMapping("/api/seckill/sku/rollback")
    Wrapper rollback(@RequestBody OrderModel order);
}
