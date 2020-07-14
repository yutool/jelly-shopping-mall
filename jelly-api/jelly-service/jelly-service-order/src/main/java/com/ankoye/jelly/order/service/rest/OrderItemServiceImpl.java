package com.ankoye.jelly.order.service.rest;

import com.ankoye.jelly.order.dao.OrderItemMapper;
import com.ankoye.jelly.order.service.OrderItemService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("restOrderItemService")
public class OrderItemServiceImpl implements OrderItemService {
    @Resource
    private OrderItemMapper orderItemMapper;

}
