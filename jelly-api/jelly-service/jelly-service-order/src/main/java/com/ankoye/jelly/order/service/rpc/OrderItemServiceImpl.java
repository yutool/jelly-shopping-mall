package com.ankoye.jelly.order.service.rpc;

import com.ankoye.jelly.order.dao.OrderItemMapper;
import com.ankoye.jelly.order.domian.Order;
import com.ankoye.jelly.order.domian.OrderItem;
import com.ankoye.jelly.order.service.OrderItemService;
import com.ankoye.jelly.web.support.BaseService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author ankoye@qq.com
 */
@Service
@Primary
public class OrderItemServiceImpl extends BaseService<OrderItem> implements OrderItemService {
    @Resource
    private OrderItemMapper orderItemMapper;

}
