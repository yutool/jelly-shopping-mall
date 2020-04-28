package com.ankoye.jelly.order.service.impl;

import com.ankoye.jelly.order.dao.OrderItemMapper;
import com.ankoye.jelly.order.service.OrderItemService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class OrderItemServiceImpl implements OrderItemService {
    @Resource
    private OrderItemMapper orderItemMapper;

}
