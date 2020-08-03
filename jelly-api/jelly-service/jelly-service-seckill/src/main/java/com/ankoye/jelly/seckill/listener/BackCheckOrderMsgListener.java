package com.ankoye.jelly.seckill.listener;

import com.ankoye.jelly.seckill.service.SeckillOrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 秒杀订单回查，待实现
 * @author ankoye@qq.com
 */
@Slf4j
@Component
@RocketMQMessageListener(
        topic = "${user-order-topic}",
        selectorExpression = "seckill-check",
        consumerGroup = "order-check-group",
        consumeMode = ConsumeMode.CONCURRENTLY
)
public class BackCheckOrderMsgListener implements RocketMQListener<String> {
    @Autowired
    private SeckillOrderService seckillOrderService;

    @Override
    public void onMessage(String orderId) {
        log.info("秒杀订单支付状态回查：{}", orderId);
        // 超时未支付，删除订单
        seckillOrderService.checkOrder(orderId);
    }
}

