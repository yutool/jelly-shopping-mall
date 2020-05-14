package com.ankoye.jelly.seckill.listener;

import com.alibaba.fastjson.JSON;
import com.ankoye.jelly.order.model.OrderModel;
import com.ankoye.jelly.seckill.service.SeckillOrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RocketMQMessageListener(
        topic = "${seckill-order-topic}",
        selectorExpression = "rollback",
        consumerGroup = "seckill-rollback-group",
        consumeMode = ConsumeMode.CONCURRENTLY
)
public class SeckillOrderRollbackMsgListener implements RocketMQListener<String> {
    @Autowired
    private SeckillOrderService seckillOrderService;

    @Override
    public void onMessage(String message) {
        log.info("MQ - 回滚秒杀订单库存");
        OrderModel orderModel = JSON.parseObject(message, OrderModel.class);
        seckillOrderService.rollback(orderModel);
    }
}
