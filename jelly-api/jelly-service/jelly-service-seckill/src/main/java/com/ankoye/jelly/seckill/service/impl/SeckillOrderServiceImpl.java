package com.ankoye.jelly.seckill.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.ankoye.jelly.order.domian.OrderItem;
import com.ankoye.jelly.order.model.OrderModel;
import com.ankoye.jelly.order.service.OrderService;
import com.ankoye.jelly.seckill.common.constant.RedisKey;
import com.ankoye.jelly.seckill.domain.SeckillSku;
import com.ankoye.jelly.seckill.model.OrderQueue;
import com.ankoye.jelly.seckill.service.SeckillOrderService;
import com.ankoye.jelly.util.IdUtils;
import com.ankoye.jelly.web.exception.CastException;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("ALL")
@Service
public class SeckillOrderServiceImpl implements SeckillOrderService {
    @Value("${seckill-order-topic}")
    private String orderTopic;

    @Reference
    private OrderService orderService;

    @Autowired
    private RocketMQTemplate rocketMQTemplate;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 排队 -> MQ 异步处理排队,预扣库存  -> 预创建订单
     * @param goodsId 秒杀商品id
     * @return false 排队失败
     */
    @Override
    public boolean queueUp(OrderQueue orderQueue) {
        String userId = orderQueue.getUserId();
        String skuId = orderQueue.getSkuId();
        // 1. 上把锁，防止恶意排队 - 不知道需不需要
        String redis_key = "seckill_queue_" + userId + "_id_" + skuId;
        long count = redisTemplate.opsForValue().increment(redis_key, 1);
        redisTemplate.expire(redis_key, 60, TimeUnit.SECONDS);
        if (count > 1) {
            CastException.cast("正在排队");
        }
        // 2.判断是否创建过订单
        OrderQueue tmp = (OrderQueue) redisTemplate.boundHashOps(RedisKey.SECKILL_USER_QUEUE).get(userId + skuId);
        if(tmp != null && tmp.getStatus() == OrderQueue.CREATED) {
            redisTemplate.delete(redis_key);
            CastException.cast("已经秒杀过该商品");
        }

        // 3.开始排队 - 排队中
        orderQueue.setCreateTime(new Date());
        orderQueue.setStatus(OrderQueue.BE_QUEUING);

        // 4. 异步处理订单，多线程  version 1
        // redisTemplate.boundListOps(RedisKey.SECKILL_QUEUE_UP).leftPush(orderQueue);
        // multiThreadingCreateOrder.createOrder();

        // 5. MQ处理订单 version 2
        // 供用户查询排队状态
        redisTemplate.boundHashOps(RedisKey.SECKILL_USER_QUEUE).put(userId + skuId, orderQueue);
        rocketMQTemplate.convertAndSend(orderTopic + ":create", JSON.toJSONString(orderQueue));
        redisTemplate.delete(redis_key);    // 解锁
        return true;
    }

    @Override
    public OrderQueue queryQueue(String userId, String skuId) {
        return (OrderQueue) redisTemplate.boundHashOps(RedisKey.SECKILL_USER_QUEUE).get(userId + skuId);
    }

    /**
     * 创建订单到Redis
     */
    @Override
    @Transactional
    public String prepare(String userId, SeckillSku sku) {
        // 创建预订单
        String orderId = IdUtils.getOrderId();
        OrderModel orderModel = new OrderModel();
        orderModel.setId(orderId);
        orderModel.setUserId(userId);
        orderModel.setType(1);      // 秒杀订单
        orderModel.setMoney(sku.getCostPrice());
        orderModel.setPayMoney(sku.getPrice());

        // sku
        OrderItem orderItem = new OrderItem(
                orderId, sku.getId(), "0", sku.getTitle(), sku.getImage(), sku.getSku(),
                sku.getPrice(), 1, sku.getPrice(), sku.getPrice()
        );

        // 添加到到 reids
        orderModel.setOrderItem(Arrays.asList(orderItem));
        redisTemplate.boundHashOps(RedisKey.PREPARE_ORDER).put(orderId, orderModel);
        return orderId;
    }

    @Override
    public void rollback(OrderModel order) {
        order.getUserId();
        // 回滚库存，删除排队状态
        for (OrderItem item : order.getOrderItem()) {
            redisTemplate.opsForValue().increment(RedisKey.SECKILL_SKU_COUNT_KEY + item.getSkuId(), item.getNum());
            redisTemplate.boundHashOps(RedisKey.SECKILL_USER_QUEUE).delete(order.getUserId() + item.getSkuId());
        }
    }

    @Override
    public int checkOrder(String orderId) {
        // 要处理秒杀库存问题
        return 0;
    }
}
