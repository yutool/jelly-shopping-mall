package com.ankoye.jelly.order.web.feign;

import com.ankoye.jelly.base.result.Wrapper;
import com.ankoye.jelly.base.result.Wrappers;
import com.ankoye.jelly.order.domian.Order;
import com.ankoye.jelly.order.feign.OrderFeign;
import com.ankoye.jelly.order.reference.OrderReference;
import com.ankoye.jelly.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ankoye@qq.com
 */
@RestController
public class OrderFeignClient implements OrderFeign {
    @Autowired
    private OrderReference orderReference;

    @Override
    public Wrapper<Order> get(String id) {
        return Wrappers.success(orderReference.selectById(id));
    }

    @Override
    public Wrapper updateById(Order order) {
        orderReference.updateById(order);
        return Wrappers.success();
    }
}
