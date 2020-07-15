package com.ankoye.jelly.seckill.web.rpc;

import com.ankoye.jelly.base.result.Result;
import com.ankoye.jelly.seckill.domain.SeckillOrder;
import com.ankoye.jelly.seckill.model.OrderQueue;
import com.ankoye.jelly.seckill.service.SeckillOrderService;
import com.ankoye.jelly.web.support.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author ankoye@qq.com
 */
@RestController
@RequestMapping("/rpc/seckill/order")
public class SeckillOrderController {

    @Autowired
    private SeckillOrderService seckillOrderService;

    /**
     * 排队
     */
    @PostMapping("/queueUp")
    public Result queueUp(@RequestBody OrderQueue orderQueue) {
        seckillOrderService.queueUp(orderQueue);
        return Result.success();
    }

    /**
     * 查询排队状态
     */
    @GetMapping("/queue/{userId}/{goodsId}")
    public Result queryQueue(@PathVariable  String userId, @PathVariable String goodsId) {
        OrderQueue order = seckillOrderService.queryQueue(userId, goodsId);
        return Result.success(order);
    }

}
