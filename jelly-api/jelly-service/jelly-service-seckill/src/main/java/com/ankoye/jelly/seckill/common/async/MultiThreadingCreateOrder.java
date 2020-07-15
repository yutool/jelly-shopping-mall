package com.ankoye.jelly.seckill.common.async;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 遗弃类
 * 目前已被 MQ 取代
 *
 * @author ankoye@qq.com
 */
@Component
public class MultiThreadingCreateOrder {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 1.获取redis中的商品信息与库存信息,并进行判断
     * 2.执行redis的预扣减库存操作，并获取扣减之后的库存值
     * 3.如果扣减之后的库存值 <= 0，则删除redis中响应的商品信息与库存信息
     * 4.基于mq完成mysql的数据同步，进行异步下单  并扣减库存(mysql)
     */
    @Async
    public void createOrder() {
//        // 1.从队伍中获取排队信息
//        OrderQueue orderQueue = (OrderQueue) redisTemplate.boundListOps(RedisKey.SECKILL_QUEUE_UP).rightPop();
//        if(orderQueue == null)
//            return;
//        String username = orderQueue.getUsername();
//        long id = orderQueue.getGoodsId();
//        String time = orderQueue.getTime();
//
//        /** 2.因为外面判断过，看看需不需要判断订单是否存在MySQL中 */
//
//        // 3.获取商品信息 - 库存信息
//        SeckillGoods seckillGoods = (SeckillGoods) redisTemplate.boundHashOps(RedisKey.SECKILL_GOODS_KEY + time).get(id);
//        String redisStock = (String) redisTemplate.opsForValue().get(RedisKey.SECKILL_GOODS_STOCK_COUNT_KEY + id);
//        if (seckillGoods == null || StringUtils.isEmpty(redisStock) || Integer.parseInt(redisStock) <= 0) {
//            // 无商品了，修改订单状态 - 排队失败
//            orderQueue.setStatus(OrderQueue.BE_FAIL);
//            redisTemplate.boundHashOps(RedisKey.SECKILL_QUEUE_UP).put(username+id, orderQueue);
//            return ;
//        }
//
//        // 4.执行redis的预扣减库存, 并获取到扣减之后的库存值，休眠测试下
//        Long decrement = redisTemplate.opsForValue().decrement(RedisKey.SECKILL_GOODS_STOCK_COUNT_KEY + id);
//        if (decrement <= 0){
//            // 扣减完库存之后,当前商品已经没有库存了.
//            // 删除redis中的商品信息与库存信息
//            redisTemplate.boundHashOps(RedisKey.SECKILL_GOODS_KEY + time).delete(id);
//            redisTemplate.delete(RedisKey.SECKILL_GOODS_STOCK_COUNT_KEY + id);
//        }
//
//        // 5. 排队成功 - 创建订单 - 通知MQ
//        orderQueue.setStatus(OrderQueue.CREATED);
//        redisTemplate.boundHashOps(RedisKey.SECKILL_QUEUE_UP).put(username+id, orderQueue);
//
//        SeckillOrder order = new SeckillOrder();
//        order.setId(Long.parseLong(IdUtils.getOrderId()));
//        order.setSeckillId(id);
//        order.setMoney(orderQueue.getMoney());
//        order.setUserId(username);
//        order.setMerchantId("0");
//        order.setCreateTime(new Date());
//        order.setStatus(1);
//
//        /** 排队成功，让MQ创建MYSQL订单，且进入付款 */
    }

}
