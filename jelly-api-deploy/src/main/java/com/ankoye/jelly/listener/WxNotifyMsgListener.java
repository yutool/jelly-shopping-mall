package com.ankoye.jelly.listener;

import com.alibaba.fastjson.JSON;
import com.ankoye.jelly.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author ankoye@qq.com
 */
@RocketMQMessageListener(
        topic = "${user-pay-topic}",
        selectorExpression = "wx-notify",   // 微信支付订单
        consumerGroup = "pay-notify-group"
)
@Component
@Slf4j
public class WxNotifyMsgListener implements RocketMQListener<String> {

    @Autowired
    private OrderService orderService;

    /** 需要全局事务管控 */
    @Override
    public void onMessage(String msg) {
        Map<String, String> resultMap = JSON.parseObject(msg, Map.class);
        String returnCode = resultMap.get("return_code");       // 通信状态
        if (returnCode.equals("SUCCESS")) {
            String resultCode = resultMap.get("result_code");   // 业务结果
            String orderId = resultMap.get("out_trade_no");     // 订单号
            String payTime = resultMap.get("time_end");         // 支付时间
            String transactionId = resultMap.get("transaction_id"); // 微信支付订单号
            if(resultCode.equals("SUCCESS")) {
                // 支付成功，修改订单状态
                log.info("微信支付回调成功，订单号：{}", orderId);
                orderService.updateStatus(orderId, payTime, transactionId);
            } else {
                // 支付失败
                orderService.payFailStatus(orderId);
                log.info("支付失败");
            }
        }
    }
}
