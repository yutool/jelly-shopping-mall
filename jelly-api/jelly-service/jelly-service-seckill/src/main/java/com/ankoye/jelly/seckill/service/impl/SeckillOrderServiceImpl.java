package com.ankoye.jelly.seckill.service.impl;

import com.ankoye.jelly.seckill.common.constant.RedisKey;
import com.ankoye.jelly.seckill.dao.SeckillOrderMapper;
import com.ankoye.jelly.seckill.domain.SeckillOrder;
import com.ankoye.jelly.seckill.model.OrderQueue;
import com.ankoye.jelly.seckill.service.SeckillOrderService;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class SeckillOrderServiceImpl implements SeckillOrderService {
    @Value("${create-seckill-order-topic}")
    private String createOrderTopic;
    @Value("${order-back-check-topic}")
    private String checkOrderTopic;

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
    public boolean queueUp(String userId, String time, String goodsId) {
        // 1.防止用户恶意排队，不一定创建过订单
        if (!preventRepeatCommit(userId, goodsId))
            return false; // 改成抛异常
        // 2.判断是否创建过订单
        OrderQueue tmp = (OrderQueue) redisTemplate.boundHashOps(RedisKey.SECKILL_QUEUE_UP).get(userId + goodsId);
        if(tmp != null && tmp.getStatus() == OrderQueue.CREATED)
            return false;
        // 3.开始排队 - 排队中
        OrderQueue orderQueue = new OrderQueue(userId, new Date(), time, goodsId, OrderQueue.BE_QUEUING);
        redisTemplate.boundListOps(RedisKey.SECKILL_QUEUE_UP).leftPush(orderQueue);
        // 4.异步处理订单，多线程 -> MQ OR 直接 MQ
        // multiThreadingCreateOrder.createOrder();
        rocketMQTemplate.convertAndSend(createOrderTopic, "");
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
        return (OrderQueue) redisTemplate.boundHashOps(RedisKey.SECKILL_QUEUE_UP).get(userId + goodsId);
    }

    @Override
    public void create(SeckillOrder order) {
        seckillOrderMapper.insert(order);
        try {
            DefaultMQProducer producer = rocketMQTemplate.getProducer();
            Message message = new Message(checkOrderTopic, "seckill-order", order.getId().getBytes());
            message.setDelayTimeLevel(16);
            producer.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
