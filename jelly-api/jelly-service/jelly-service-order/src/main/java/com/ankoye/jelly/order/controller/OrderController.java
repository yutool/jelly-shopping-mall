package com.ankoye.jelly.order.controller;

import com.ankoye.jelly.base.result.Result;
import com.ankoye.jelly.order.model.OrderDto;
import com.ankoye.jelly.order.service.OrderService;
import com.ankoye.jelly.web.log.annotation.Logger;
import com.ankoye.jelly.web.log.constant.LogType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/v1/order")
@Slf4j
public class OrderController {

    @Resource
    private OrderService orderService;

    @GetMapping("/{id}")
    public Result findById(@PathVariable String id) {
        OrderDto order = orderService.getOrderById(id);
        return Result.success(order);
    }

    @Logger(module = "订单模块", operation = "创建订单")
    @PostMapping("/create")
    public Result createOrder(@RequestBody OrderDto orderDto) {
        // 创建订单
        String orderId = orderService.createOrder(orderDto);
        return Result.success(orderId);
    }

    @Logger(module = "订单模块", operation = "获取用户所有订单", exclude = {LogType.RESPONSE, LogType.REQUEST})
    @GetMapping("/user/{id}")
    public Result findByUserId(@PathVariable String id) {
        List<OrderDto> orders = orderService.getByUserId(id);
        return Result.success(orders);
    }

    @Logger(module = "订单模块", operation = "删除订单")
    @DeleteMapping("/{id}")
    public Result deleteById(@PathVariable String id) {
        orderService.deleteById(id);
        return Result.success();
    }

    @GetMapping("/testT")
    public Result testT() {
        orderService.test();
        return Result.success();
    }
}
