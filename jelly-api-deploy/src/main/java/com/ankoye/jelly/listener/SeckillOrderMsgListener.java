package com.ankoye.jelly.listener;

import com.alibaba.fastjson.JSON;
import com.ankoye.jelly.common.constant.SeckillKey;
import com.ankoye.jelly.common.exception.CastException;
import com.ankoye.jelly.domain.SeckillSku;
import com.ankoye.jelly.model.OrderQueue;
import com.ankoye.jelly.model.SeckillGoods;
import com.ankoye.jelly.service.SeckillOrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.stream.Collectors;

/**
 * @author ankoye@qq.com
 */
@Slf4j
@Component
@RocketMQMessageListener(
        topic = "${seckill-order-topic}",
        selectorExpression = "create",
        consumerGroup = "seckill-order-group",
        consumeMode = ConsumeMode.ORDERLY
)
public class SeckillOrderMsgListener implements RocketMQListener<String> {
    @Autowired
    private SeckillOrderService seckillOrderService;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 1.获取redis中的商品信息与库存信息,并进行判断
     * 2.执行redis的预扣减库存操作，并获取扣减之后的库存值
     * 3.如果扣减之后的库存值 <= 0，则删除redis中响应的商品信息与库存信息
     * 4.预创建订单
     * 5.修改排队状态
     */
    @Override
    public void onMessage(String message) {
        log.info("MQ - 处理排队信息");
        // 1. 获取排队信息
        OrderQueue orderQueue = JSON.parseObject(message, OrderQueue.class);
        String userId = orderQueue.getUserId();
        String time = orderQueue.getTime();
        String skuId = orderQueue.getSkuId();
        int num = orderQueue.getNum();

        // 2.执行redis的预扣减库存, 并获取到扣减之后的库存值
        Long decrement = redisTemplate.opsForValue().decrement(SeckillKey.SKU_COUNT_PRE + skuId, num);
        if (decrement < 0){
            // 扣减失败 - 回滚库存并删除排队状态
            redisTemplate.opsForValue().increment(SeckillKey.SKU_COUNT_PRE + skuId, num);
            redisTemplate.boundHashOps(SeckillKey.USER_QUEUE).delete(userId+skuId);
            CastException.cast("商品库存不足，秒杀失败");
        }

        // 3.创建预订单
        SeckillGoods seckillGoods = (SeckillGoods) redisTemplate.boundHashOps(SeckillKey.GOODS_PRE + time).get(orderQueue.getSpuId());
        SeckillSku seckillSku = seckillGoods.getSkuList().stream()
                .filter(item -> item.getId().equals(skuId))
                .collect(Collectors.toList()).get(0);
        String orderId = seckillOrderService.prepare(userId, seckillSku);

        // 4.修改状态，预创建订单成功，用户收到这状态请求支付
        orderQueue.setStatus(OrderQueue.CREATED);
        orderQueue.setOrderId(orderId);
        redisTemplate.boundHashOps(SeckillKey.USER_QUEUE).put(userId + skuId, orderQueue);
    }
}
