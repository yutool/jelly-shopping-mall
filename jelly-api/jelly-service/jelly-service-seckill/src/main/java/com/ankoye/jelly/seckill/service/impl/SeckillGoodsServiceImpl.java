package com.ankoye.jelly.seckill.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ankoye.jelly.base.constant.GoodsStatus;
import com.ankoye.jelly.goods.domain.Sku;
import com.ankoye.jelly.goods.service.SkuService;
import com.ankoye.jelly.seckill.common.constant.RedisKey;
import com.ankoye.jelly.seckill.dao.SeckillGoodsMapper;
import com.ankoye.jelly.seckill.domain.SeckillGoods;
import com.ankoye.jelly.seckill.service.SeckillGoodsService;
import com.ankoye.jelly.util.IdUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class SeckillGoodsServiceImpl implements SeckillGoodsService {
    @Reference
    private SkuService skuService;
    @Resource
    private SeckillGoodsMapper seckillGoodsMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public PageInfo<SeckillGoods> list(Integer page, Integer size) {
        PageHelper.startPage(page, size);
        List<SeckillGoods> seckillGoods = seckillGoodsMapper.selectList(new QueryWrapper<SeckillGoods>()
                .eq("merchant_id", 0)
        );
        return new PageInfo<>(seckillGoods);
    }

    @Override
    @Transactional // 全局事务
    public void add(SeckillGoods goods) {
        // 查询商品信息
        Sku sku = skuService.getSkuById(goods.getSkuId());
        // 添加秒杀商品
        goods.setId(IdUtils.getSpuId());
        goods.setSpuId(sku.getSpuId());
        goods.setImage(sku.getImage());
        goods.setMerchantId("0");
        goods.setStockCount(goods.getNum());
        goods.setCreateTime(new Date());
        goods.setIsMarketable(true);
        goods.setStatus(GoodsStatus.SUCCESS);
        seckillGoodsMapper.insert(goods);
        // 冻结商品库存
        skuService.freezeScore(goods.getSkuId(), goods.getNum());
    }

    @Override
    public List<SeckillGoods> timeList(String time) {
        return redisTemplate.boundHashOps(RedisKey.SECKILL_GOODS_KEY + time).values();
    }

    @Override
    public SeckillGoods detail(String time, String goodsId) {
        return (SeckillGoods) redisTemplate.boundHashOps(RedisKey.SECKILL_GOODS_KEY + time).get(goodsId);
    }
}
