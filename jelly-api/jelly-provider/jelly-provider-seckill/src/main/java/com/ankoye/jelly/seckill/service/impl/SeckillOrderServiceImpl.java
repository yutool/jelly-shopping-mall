package com.ankoye.jelly.seckill.service.impl;

import com.alibaba.fastjson.JSON;
import com.ankoye.jelly.base.constant.OrderStatus;
import com.ankoye.jelly.order.domian.Order;
import com.ankoye.jelly.order.domian.OrderItem;
import com.ankoye.jelly.order.feign.OrderFeign;
import com.ankoye.jelly.order.feign.OrderItemFeign;
import com.ankoye.jelly.order.model.OrderModel;
import com.ankoye.jelly.seckill.common.constant.SeckillKey;
import com.ankoye.jelly.seckill.domain.SeckillSku;
import com.ankoye.jelly.seckill.model.OrderQueue;
import com.ankoye.jelly.seckill.service.SeckillOrderService;
import com.ankoye.jelly.util.DateUtils;
import com.ankoye.jelly.util.IdUtils;
import com.ankoye.jelly.util.RedisLock;
import com.ankoye.jelly.web.exception.CastException;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author ankoye@qq.com
 */
@SuppressWarnings("ALL")
@Service
@Primary
public class SeckillOrderServiceImpl implements SeckillOrderService {
    @Value("${seckill-order-topic}")
    private String orderTopic;

    @Autowired
    private RocketMQTemplate rocketMQTemplate;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private OrderFeign orderFeign;
    @Autowired
    private OrderItemFeign orderItemFeign;

    /**
     * 排队 -> MQ 异步处理排队,预扣库存  -> 预创建订单
     * @param goodsId 秒杀商品id
     * @return false 排队失败
     */
    @Override
    public boolean queueUp(OrderQueue orderQueue) {
        String menu = DateUtils.currentMenu();
        if (!menu.equals(orderQueue.getTime())) {
            CastException.cast("该商品暂未开始秒杀");
        }
        String userId = orderQueue.getUserId();
        String skuId = orderQueue.getSkuId();

        // 1. 判断商品库存是否充足 - 可以判断秒杀数量>设置的最大数量
        String count = (String) redisTemplate.opsForValue().get(SeckillKey.SKU_COUNT_PRE + skuId);
        if (count == null && Integer.parseInt(count) < orderQueue.getNum()) {
            CastException.cast("商品库存不足");
        }
        // 2. 上把锁，防止恶意排队
        String redisKey = "seckill_queue_" + userId + "_id_" + skuId;
        String identify = RedisLock.tryLock(redisKey, 2000);
        if (identify == null) {
            CastException.cast("正在排队中...");
        }
        // 3. 判断是否秒杀过商品
        OrderQueue tmp = (OrderQueue) redisTemplate.boundHashOps(SeckillKey.USER_QUEUE).get(userId + skuId);
        if(tmp != null) {
            RedisLock.unlock(redisKey, identify);
            CastException.cast("请勿重复秒杀...");
        }
        // 4.开始排队 - 排队中
        orderQueue.setCreateTime(new Date());
        orderQueue.setStatus(OrderQueue.QUEUING);
        // 5. MQ处理订单
        redisTemplate.boundHashOps(SeckillKey.USER_QUEUE).put(userId + skuId, orderQueue);
        rocketMQTemplate.convertAndSend(orderTopic + ":create", JSON.toJSONString(orderQueue));
        RedisLock.unlock(redisKey, identify);
        return true;
    }

    @Override
    public OrderQueue queryQueue(String userId, String skuId) {
        return (OrderQueue) redisTemplate.boundHashOps(SeckillKey.USER_QUEUE).get(userId + skuId);
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
        // 秒杀订单
        orderModel.setType(1);
        orderModel.setMoney(sku.getPrice());
        orderModel.setPayMoney(sku.getPrice());
        // sku
        OrderItem orderItem = new OrderItem(
                orderId, sku.getSpuId(), sku.getId(), "0", sku.getTitle(), sku.getImage(),
                sku.getSku(), sku.getOriginalPrice(), sku.getPrice(),  1, sku.getPrice()
        );
        // 添加到到 reids
        orderModel.setOrderItem(Arrays.asList(orderItem));
        redisTemplate.boundHashOps(SeckillKey.PREPARE_ORDER).put(orderId, orderModel);
        return orderId;
    }

    @Override
    public boolean checkOrder(String orderId) {
        Order orderData = orderFeign.get(orderId).getData();
        if (orderData == null) {
            CastException.cast("订单不存在");
        }
        if (Objects.equals(orderData.getStatus(), OrderStatus.WAIT_PAY)) {
            // 关闭订单
            orderFeign.updateById(new Order().setId(orderId).setStatus(OrderStatus.CLOSE));
            // 回滚库存，删除排队状态
            String userId = orderData.getUserId();
            List<OrderItem> itemData = orderItemFeign.getOrderItem(orderId).getData();
            for (OrderItem item : itemData) {
                redisTemplate.opsForValue().increment(SeckillKey.SKU_COUNT_PRE + item.getSkuId(), item.getNum());
                redisTemplate.boundHashOps(SeckillKey.USER_QUEUE).delete(userId + item.getSkuId());
            }
            /** 关闭支付状态 */
        }
        return true;
    }
}
