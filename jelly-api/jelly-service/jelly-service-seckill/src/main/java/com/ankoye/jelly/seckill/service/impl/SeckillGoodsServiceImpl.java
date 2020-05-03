package com.ankoye.jelly.seckill.service.impl;

import com.ankoye.jelly.seckill.domain.SeckillGoods;
import com.ankoye.jelly.seckill.common.constant.RedisKey;
import com.ankoye.jelly.seckill.service.SeckillGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeckillGoodsServiceImpl implements SeckillGoodsService {
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public List<SeckillGoods> list(String time) {
        return redisTemplate.boundHashOps(RedisKey.SECKILL_GOODS_KEY + time).values();
    }

    @Override
    public SeckillGoods detail(String time, String goodsId) {
        return (SeckillGoods) redisTemplate.boundHashOps(RedisKey.SECKILL_GOODS_KEY + time).get(goodsId);
    }
}
