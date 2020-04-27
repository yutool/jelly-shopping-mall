package com.ankoye.jelly.order.controller;

import com.ankoye.jelly.base.result.Result;
import com.ankoye.jelly.order.domian.Order;
import com.ankoye.jelly.order.model.OrderDto;
import com.ankoye.jelly.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/order")
@Slf4j
public class OrderController {
    @Resource
    private OrderService orderService;

    @GetMapping("/{id}")
    public Result<Order> getOrder(@PathVariable String id) {
        Order order = orderService.getOrderById(id);
        return new Result<Order>().success(order);
    }

    @PostMapping("/create")
    public Result<String> createOrder(@RequestBody OrderDto orderDto) {
        String orderSn = orderService.createOrder(orderDto);
        log.info(orderDto.toString());
        return new Result<String>().success(orderSn);
    }
}
