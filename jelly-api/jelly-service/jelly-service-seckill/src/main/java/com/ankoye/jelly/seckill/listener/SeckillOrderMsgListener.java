package com.ankoye.jelly.seckill.listener;

import com.alibaba.fastjson.JSON;
import com.ankoye.jelly.seckill.common.constant.RedisKey;
import com.ankoye.jelly.seckill.domain.SeckillSku;
import com.ankoye.jelly.seckill.model.OrderQueue;
import com.ankoye.jelly.seckill.model.SeckillGoods;
import com.ankoye.jelly.seckill.service.SeckillOrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

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
        if(orderQueue.getSpuId() == null)
            return;

        /** 因为外面判断过，看看需不需要判断订单是否存在MySQL中 */

        // 2. 获取秒杀商品中和库存信息
        SeckillGoods seckillGoods = (SeckillGoods) redisTemplate.boundHashOps(RedisKey.SECKILL_GOODS + time).get(orderQueue.getSpuId());
        String redisStock = (String) redisTemplate.opsForValue().get(RedisKey.SECKILL_SKU_COUNT_KEY + skuId);
        if (seckillGoods == null || StringUtils.isEmpty(redisStock) || Integer.parseInt(redisStock) <= 0) {
            // 无商品了，修改订单状态 - 排队失败
            redisTemplate.boundHashOps(RedisKey.SECKILL_USER_QUEUE).put(userId + skuId, orderQueue);
            return ;
        }

        // 3. 获取秒杀商品中对应的sku
        SeckillSku seckillSku = null;
        for (SeckillSku sku : seckillGoods.getSkuList()) {
            if (sku.getId().equals(skuId)) {
                seckillSku = sku;
            }
        }

        // 4.执行redis的预扣减库存, 并获取到扣减之后的库存值
        Long decrement = redisTemplate.opsForValue().decrement(RedisKey.SECKILL_SKU_COUNT_KEY + skuId);
        if (decrement <= 0){
            // 扣减完库存之后,当前商品已经没有库存了.
            // 删除redis中的商品信息与库存信息
            redisTemplate.boundHashOps(RedisKey.SECKILL_GOODS + time).delete(skuId);
            redisTemplate.delete(RedisKey.SECKILL_SKU_COUNT_KEY + skuId);
        }

        // 5.创建订单
        String orderId = seckillOrderService.prepare(userId, seckillSku);

        // 6.修改状态，创建订单成功，用户收到这状态请求支付
        orderQueue.setStatus(OrderQueue.CREATED);
        orderQueue.setOrderId(orderId);
        redisTemplate.boundHashOps(RedisKey.SECKILL_USER_QUEUE).put(userId + skuId, orderQueue);
    }
}
