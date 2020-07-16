package com.ankoye.jelly.order.web.frontend;

import com.ankoye.jelly.base.result.Result;
import com.ankoye.jelly.order.model.OrderModel;
import com.ankoye.jelly.order.service.OrderService;
import com.ankoye.jelly.web.log.annotation.Logger;
import com.ankoye.jelly.web.log.constant.LogType;
import com.ankoye.jelly.web.support.BaseController;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author ankoye@qq.com
 */
@CrossOrigin
@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController extends BaseController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/{id}")
    public Result findById(@PathVariable String id) {
        OrderModel order = orderService.getOrderById(id);
        return Result.success(order);
    }

    @Logger(module = "订单模块", operation = "预创建订单")
    @PostMapping("/prepare")
    public Result prepareOrder(@RequestBody OrderModel orderModel) {
        String orderId = orderService.prepare(orderModel);
        return handleResult(orderId);
    }

    @DeleteMapping("/prepare/{id}")
    public Result deletePrepareOrder(@PathVariable String id) {
        orderService.checkPrepareOrder(id);
        return Result.success();
    }

    @GetMapping("/prepare/{id}")
    public Result getPrepareOrder(@PathVariable String id) {
        OrderModel orderModel = orderService.getPrepareOrder(id);
        return Result.success(orderModel);
    }

    @Logger(module = "订单模块", operation = "创建订单")
    @PostMapping("/create")
    public Result create(@RequestBody OrderModel orderModel) {
        // 创建订单
        String orderId = orderService.create(orderModel);
        return handleResult(orderId);
    }

    @Logger(module = "订单模块", operation = "获取用户所有订单", exclude = {LogType.RESPONSE, LogType.REQUEST})
    @GetMapping("/user/{id}/{page}/{size}")
    public Result findByUserId(@PathVariable String id, @PathVariable Integer page, @PathVariable Integer size) {
        PageInfo<OrderModel> orders = orderService.getByUserId(id, page, size);
        return Result.success(orders);
    }

    @Logger(module = "订单模块", operation = "删除订单")
    @DeleteMapping("/{id}")
    public Result deleteById(@PathVariable String id) {
        return handleResult(orderService.deleteById(id));
    }
}
