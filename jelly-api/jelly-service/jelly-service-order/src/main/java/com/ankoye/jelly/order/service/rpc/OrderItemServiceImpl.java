package com.ankoye.jelly.order.service.rpc;

import com.ankoye.jelly.order.dao.OrderItemMapper;
import com.ankoye.jelly.order.service.OrderItemService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Primary
public class OrderItemServiceImpl implements OrderItemService {
    @Resource
    private OrderItemMapper orderItemMapper;

}
