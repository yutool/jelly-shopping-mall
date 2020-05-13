package com.ankoye.jelly.seckill.listener;

import com.alibaba.fastjson.JSON;
import com.ankoye.jelly.seckill.common.constant.RedisKey;
import com.ankoye.jelly.seckill.domain.SeckillSku;
import com.ankoye.jelly.seckill.model.OrderQueue;
import com.ankoye.jelly.seckill.model.SeckillGoods;
import com.ankoye.jelly.seckill.service.SeckillOrderService;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@SuppressWarnings("ALL")
@RocketMQMessageListener(
        topic = "${seckill-order-topic}",
        selectorExpression = "create",
        consumerGroup = "seckill-order-group",
        consumeMode = ConsumeMode.ORDERLY
)
@Component
public class SeckillOrderMsgListener implements RocketMQListener<String> {
    @Autowired
    private SeckillOrderService seckillOrderService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 1.获取redis中的商品信息与库存信息,并进行判断
     * 2.执行redis的预扣减库存操作，并获取扣减之后的库存值
     * 3.如果扣减之后的库存值 <= 0，则删除redis中响应的商品信息与库存信息
     * 4.基于mq完成mysql的数据同步，进行异步下单  并扣减库存(mysql)
     */
    @Override
    public void onMessage(String msg) {
        // 获取排队信息
        OrderQueue orderQueue = JSON.parseObject(msg, OrderQueue.class);
        System.out.println("接收到消息");
        String userId = orderQueue.getUserId();
        String time = orderQueue.getTime();
        String goodsId = orderQueue.getGoodsId();
        if(orderQueue.getSpuId() == null)
            return;


        /** 因为外面判断过，看看需不需要判断订单是否存在MySQL中 */

        // 2. 获取秒杀商品中和库存信息
        SeckillGoods seckillGoods = (SeckillGoods) redisTemplate.boundHashOps(RedisKey.SECKILL_GOODS_KEY + time).get(orderQueue.getSpuId());
        String redisStock = (String) redisTemplate.opsForValue().get(RedisKey.SECKILL_GOODS_STOCK_COUNT_KEY + goodsId);
        if (seckillGoods == null || StringUtils.isEmpty(redisStock) || Integer.parseInt(redisStock) <= 0) {
            // 无商品了，修改订单状态 - 排队失败
            orderQueue.setStatus(OrderQueue.BE_FAIL);
            redisTemplate.boundHashOps(RedisKey.SECKILL_GOODS_ORDER).put(userId + goodsId, orderQueue);
            return ;
        }

        // 3. 获取秒杀商品中对应的sku
        SeckillSku seckillSku = null;
        for (SeckillSku sku : seckillGoods.getSkuList()) {
            if (sku.getId().equals(goodsId)) {
                seckillSku = sku;
            }
        }

        // 4.执行redis的预扣减库存, 并获取到扣减之后的库存值
        Long decrement = redisTemplate.opsForValue().decrement(RedisKey.SECKILL_GOODS_STOCK_COUNT_KEY + goodsId);
        if (decrement <= 0){
            // 扣减完库存之后,当前商品已经没有库存了.
            // 删除redis中的商品信息与库存信息
            redisTemplate.boundHashOps(RedisKey.SECKILL_GOODS_KEY + time).delete(goodsId);
            redisTemplate.delete(RedisKey.SECKILL_GOODS_STOCK_COUNT_KEY + goodsId);
        }

        // 5.创建订单
        String orderId = seckillOrderService.create(userId, seckillSku);

        // 6.修改状态，创建订单成功，用户收到这状态请求支付
        orderQueue.setStatus(OrderQueue.CREATED);
        orderQueue.setOrderId(orderId);
        redisTemplate.boundHashOps(RedisKey.SECKILL_GOODS_ORDER).put(userId + goodsId, orderQueue);
    }
}
