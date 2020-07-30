package com.ankoye.jelly.order.feign;

import com.ankoye.jelly.base.result.Wrapper;
import com.ankoye.jelly.order.domian.OrderItem;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author ankoye@qq.com
 */
@FeignClient(value = "jelly-order-serve", contextId = "orderItem")
public interface OrderItemFeign {
    @GetMapping("/orderItem/{orderId}")
    Wrapper<List<OrderItem>> getOrderItem(@PathVariable("orderId") String id);
}
