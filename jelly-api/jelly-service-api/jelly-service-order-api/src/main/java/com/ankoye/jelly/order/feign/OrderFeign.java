package com.ankoye.jelly.order.feign;

import com.ankoye.jelly.base.result.Wrapper;
import com.ankoye.jelly.order.domian.Order;
import com.ankoye.jelly.order.domian.OrderItem;
import org.apache.ibatis.annotations.Update;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author ankoye@qq.com
 */
@FeignClient(value = "jelly-order-serve", contextId = "order")
public interface OrderFeign {

    @GetMapping("/order/{id}")
    Wrapper<Order> get(@PathVariable("id") String id);

    @PutMapping("/order")
    Wrapper updateById(@RequestBody Order order);
}
