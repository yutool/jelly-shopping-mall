package com.ankoye.jelly.order.listener;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Map;

@RocketMQMessageListener(
        consumerGroup = "${rocketmq.consumer.group}",
        topic = "wx-pay-notify",
        consumeMode = ConsumeMode.ORDERLY
)
@Component
@Slf4j
public class PayNotifyMsgListener implements RocketMQListener<String> {

    @Override
    public void onMessage(String msg) {
        Map<String, String> resultMap = JSON.parseObject(msg, Map.class);
        String returnCode = resultMap.get("return_code");   //状态
        String outTradeNo = resultMap.get("out_trade_no");  //商户订单号
        if (returnCode.equals("SUCCESS")) {
            if (!StringUtils.isEmpty(outTradeNo)) {
                log.info("微信手机支付回调成功,订单号:{}", outTradeNo);
                /**
                 * 据业务流程，修改数据库订单支付状态，和其他数据的相应状态
                 */
            }
        } else {
            // 支付失败，关闭支付，取消订单，混滚库存
            log.info("支付失败");
        }

    }
}
