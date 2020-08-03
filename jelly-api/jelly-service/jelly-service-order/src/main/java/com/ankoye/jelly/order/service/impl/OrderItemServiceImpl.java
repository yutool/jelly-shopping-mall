package com.ankoye.jelly.order.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.ankoye.jelly.order.domian.OrderItem;
import com.ankoye.jelly.order.reference.OrderItemReference;
import com.ankoye.jelly.order.service.OrderItemService;
import com.ankoye.jelly.web.support.BaseService;
import org.springframework.stereotype.Component;

/**
 * @author ankoye@qq.com
 */
@Service
@Component
public class OrderItemServiceImpl extends BaseService<OrderItem>
        implements OrderItemService, OrderItemReference {
}
