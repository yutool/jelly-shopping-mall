package com.ankoye.jelly.order.service.impl;

import com.ankoye.jelly.base.constant.OrderStatus;
import com.ankoye.jelly.order.dao.OrderItemMapper;
import com.ankoye.jelly.order.dao.OrderMapper;
import com.ankoye.jelly.order.domian.Order;
import com.ankoye.jelly.order.domian.OrderItem;
import com.ankoye.jelly.order.model.OrderDto;
import com.ankoye.jelly.order.service.OrderService;
import com.ankoye.jelly.util.IdUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private OrderItemMapper orderItemMapper;

    @Override
    public Order getOrderById(String id) {
        return orderMapper.selectById(id);
    }

    @Override
    @Transactional
    public String createOrder(OrderDto orderDto) {
        // 处理订单，需要删除库存？还是支付完成扣？
        String orderId = IdUtils.getOrderId();
        Order order = orderDto.convertToOrder();
        order.setId(orderId);
        Date now = new Date();
        order.setCreateTime(now);
        order.setStatus(OrderStatus.WAIT_PAY);
        orderMapper.insert(order);

        List<OrderItem> orderItem = orderDto.getOrderItem();
        for (OrderItem item : orderItem) {
            item.setOrderId(orderId);
            orderItemMapper.insert(item);
        }

        /* 发送延迟消息，半个小时，如发现未支付，则取消这个订单 */
        return orderId;
    }
}