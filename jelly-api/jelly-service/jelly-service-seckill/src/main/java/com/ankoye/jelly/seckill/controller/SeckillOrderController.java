package com.ankoye.jelly.seckill.controller;

import com.ankoye.jelly.base.result.Result;
import com.ankoye.jelly.seckill.model.OrderQueue;
import com.ankoye.jelly.seckill.service.SeckillOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/seckill/order")
public class SeckillOrderController {

    @Autowired
    private SeckillOrderService seckillOrderService;

    /**
     * 排队
     */
    @GetMapping("/queueUp")
    public Result queueUp(String userId, String time, String goodsId) {
        seckillOrderService.queueUp(userId, time, goodsId);
        return Result.success();
    }

    /**
     * 查询排队状态
     */
    @GetMapping("/query")
    public Result query(String username, String goodsId) {
        OrderQueue order = seckillOrderService.query(username, goodsId);
        return Result.success(order);
    }
}
