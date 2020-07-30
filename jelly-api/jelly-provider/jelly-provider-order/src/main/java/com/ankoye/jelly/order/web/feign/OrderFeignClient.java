package com.ankoye.jelly.order.web.feign;

import com.ankoye.jelly.base.result.Wrapper;
import com.ankoye.jelly.base.result.Wrappers;
import com.ankoye.jelly.order.domian.Order;
import com.ankoye.jelly.order.feign.OrderFeign;
import com.ankoye.jelly.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * @author ankoye@qq.com
 */
public class OrderFeignClient implements OrderFeign {
    @Autowired
    private OrderService orderService;

    @Override
    public Wrapper<Order> get(String id) {
        return Wrappers.success(orderService.selectById(id));
    }

    @Override
    public Wrapper updateById(Order order) {
        orderService.updateById(order);
        return Wrappers.success();
    }
}
