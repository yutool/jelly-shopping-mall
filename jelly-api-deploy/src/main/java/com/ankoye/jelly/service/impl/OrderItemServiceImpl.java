package com.ankoye.jelly.service.impl;

import com.ankoye.jelly.common.support.BaseService;
import com.ankoye.jelly.domain.OrderItem;
import com.ankoye.jelly.service.OrderItemService;
import com.ankoye.jelly.service.reference.OrderItemReference;
import org.springframework.stereotype.Service;

/**
 * @author ankoye@qq.com
 */
@Service
public class OrderItemServiceImpl extends BaseService<OrderItem>
        implements OrderItemService, OrderItemReference {
}
