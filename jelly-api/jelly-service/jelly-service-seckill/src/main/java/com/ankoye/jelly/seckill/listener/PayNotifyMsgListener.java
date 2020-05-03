package com.ankoye.jelly.seckill.listener;

import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;

@RocketMQMessageListener(
        topic = "${pay-notify-topic}",
        selectorExpression = "wx-seckill-order",   // 微信支付的秒杀订单
        consumerGroup = "pay-notify-group",
        consumeMode = ConsumeMode.CONCURRENTLY
)
public class PayNotifyMsgListener implements RocketMQListener<String> {
    @Override
    public void onMessage(String s) {
        // ...
    }
}