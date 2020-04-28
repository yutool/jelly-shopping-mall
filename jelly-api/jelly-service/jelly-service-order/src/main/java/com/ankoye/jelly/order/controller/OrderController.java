package com.ankoye.jelly.order.controller;

import com.ankoye.jelly.base.result.Result;
import com.ankoye.jelly.order.domian.Order;
import com.ankoye.jelly.order.model.OrderDto;
import com.ankoye.jelly.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/order")
@Slf4j
public class OrderController {
    @Value("${order-back-check-topic}")
    private String orderBackTopic;

    @Autowired
    private RocketMQTemplate rocketMQTemplate;
    @Resource
    private OrderService orderService;

    @GetMapping("/{id}")
    public Result<Order> getOrder(@PathVariable String id) {
        Order order = orderService.getOrderById(id);
        return new Result<Order>().success(order);
    }

    //@LogAnnotation(module = "订单模块", operation = "创建订单")
    @PostMapping("/create")
    public Result<String> createOrder(@RequestBody OrderDto orderDto) throws Exception {
        // 创建订单
        String orderId = orderService.createOrder(orderDto);
        // 发送延迟消息，检查订单状态，发现超时未支付则取消这个订单
        DefaultMQProducer producer = rocketMQTemplate.getProducer();
        Message msg = new Message(orderBackTopic, orderId.getBytes());
        msg.setDelayTimeLevel(16);  // 半小时
        producer.send(msg);
        return new Result<String>().success(orderId);
    }
}
