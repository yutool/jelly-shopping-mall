package com.ankoye.jelly.seckill.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.ankoye.jelly.order.domian.OrderItem;
import com.ankoye.jelly.order.model.OrderDto;
import com.ankoye.jelly.order.service.OrderService;
import com.ankoye.jelly.seckill.common.constant.RedisKey;
import com.ankoye.jelly.seckill.dao.SeckillOrderMapper;
import com.ankoye.jelly.seckill.domain.SeckillSku;
import com.ankoye.jelly.seckill.model.OrderQueue;
import com.ankoye.jelly.seckill.service.SeckillOrderService;
import com.ankoye.jelly.web.exception.CastException;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

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
    @Autowired
    private RedisTemplate redisTemplate;
    @Resource
    private SeckillOrderMapper seckillOrderMapper;

    /**
     * 排队 -> 异步处理排队 -> MQ通知创建订单，付款
     * @param goodsId 秒杀商品id
     * @return false 排队失败
     */
    @Override
    public boolean queueUp(OrderQueue orderQueue) {
        String userId = orderQueue.getUserId();
        String goodsId = orderQueue.getGoodsId();
        // 1.防止用户恶意排队，不一定创建过订单
        if (!preventRepeatCommit(userId, goodsId))
            CastException.cast("已经排过队");
        // 2.判断是否创建过订单
        OrderQueue tmp = (OrderQueue) redisTemplate.boundHashOps(RedisKey.SECKILL_GOODS_ORDER).get(userId + goodsId);
        if(tmp != null && tmp.getStatus() == OrderQueue.CREATED)
            CastException.cast("已经创建过订单");
        // 3.开始排队 - 排队中
        orderQueue.setCreateTime(new Date());
        orderQueue.setStatus(OrderQueue.BE_QUEUING);
        // 4. 异步处理订单，多线程  version 1
        // redisTemplate.boundListOps(RedisKey.SECKILL_QUEUE_UP).leftPush(orderQueue);
        // multiThreadingCreateOrder.createOrder();
        // 4. MQ处理订单 version 2
        // 供用户查询排队状态
        redisTemplate.boundHashOps(RedisKey.SECKILL_GOODS_ORDER).put(userId + goodsId, orderQueue);
        rocketMQTemplate.convertAndSend(orderTopic + ":create", JSON.toJSONString(orderQueue));
        return true;
    }
    private Boolean preventRepeatCommit(String userId, String goodsId){
        String redis_key = "seckill_user_" + userId + "_id_" + goodsId;
        long count = redisTemplate.opsForValue().increment(redis_key, 1);
        if (count == 1){
            // 代表当前用户是第一次访问.
            // 对当前的key设置一个五分钟的有效期
            redisTemplate.expire(redis_key,5, TimeUnit.MINUTES);
            return true;
        }
        return false;
    }

    @Override
    public OrderQueue query(String userId, String goodsId) {
        return (OrderQueue) redisTemplate.boundHashOps(RedisKey.SECKILL_GOODS_ORDER).get(userId + goodsId);
    }

    @Override
    public String create(String userId, SeckillSku sku) {
        OrderDto orderDto = new OrderDto();
        orderDto.setUserId(userId);
        orderDto.setMoney(sku.getCostPrice());
        orderDto.setPayMoney(sku.getPrice());
        // sku
        OrderItem orderItem = new OrderItem();
        orderItem.setSkuId(sku.getSkuId());
        orderItem.setName(sku.getTitle());
        orderItem.setImage(sku.getImage());
        orderItem.setSku(sku.getSku());
        orderItem.setNum(1);
        orderItem.setPrice(sku.getPrice());
        orderDto.setMoney(sku.getPrice());
        orderItem.setPayMoney(sku.getPrice());
        // 创建订单
        orderDto.setOrderItem(Arrays.asList(orderItem));

        return orderService.createOrder(orderDto);
    }
}
