package com.ankoye.jelly.seckill.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ankoye.jelly.base.constant.GoodsStatus;
import com.ankoye.jelly.goods.domain.Sku;
import com.ankoye.jelly.goods.service.SkuService;
import com.ankoye.jelly.seckill.common.constant.RedisKey;
import com.ankoye.jelly.seckill.dao.SeckillGoodsMapper;
import com.ankoye.jelly.seckill.domain.SeckillSku;
import com.ankoye.jelly.seckill.model.SeckillGoods;
import com.ankoye.jelly.seckill.service.SeckillGoodsService;
import com.ankoye.jelly.web.exception.CastException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Service
public class SeckillGoodsServiceImpl implements SeckillGoodsService {
    @Reference
    private SkuService skuService;
    @Resource
    private SeckillGoodsMapper seckillGoodsMapper;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public PageInfo<SeckillSku> list(Integer page, Integer size) {
        PageHelper.startPage(page, size);
        List<SeckillSku> seckillGoods = seckillGoodsMapper.selectList(null);
        return new PageInfo<>(seckillGoods);
    }

    @Override
    @Transactional // 全局事务
    public void add(SeckillSku goods) {
        // 查看已经是秒杀商品
        if(seckillGoodsMapper.selectById(goods.getId()) != null) {
            CastException.cast("该商品已参加秒杀");
        }
        // 查询商品信息
        Sku sku = skuService.getSkuById(goods.getId());
        // 添加秒杀商品
        goods.setSpuId(sku.getSpuId());
        goods.setSku(sku.getSku());
        goods.setImage(sku.getImage());
        goods.setResidue(goods.getNum());
        goods.setCreateTime(new Date());
        goods.setIsMarketable(true);
        goods.setStatus(GoodsStatus.SUCCESS);
        seckillGoodsMapper.insert(goods);
        // 冻结商品库存
        skuService.freezeScore(goods.getId(), goods.getNum());
    }

    @Override
    public List<SeckillGoods> timeList(String time) {
        List<SeckillGoods> goodsList = new LinkedList<>();
        List<Object> list =  redisTemplate.boundHashOps(RedisKey.SECKILL_GOODS + time).values();
        for (Object o : list) {
            SeckillGoods seckillGoods = (SeckillGoods) o;
            for (SeckillSku sku : seckillGoods.getSkuList()) {
                Integer num = Integer.valueOf((String) redisTemplate.opsForValue().get(RedisKey.SECKILL_SKU_COUNT_KEY + sku.getId()));
                sku.setResidue(num);
            }
            goodsList.add(seckillGoods);
        }
        return goodsList;
    }

    @Override
    public SeckillGoods detail(String time, String spuId) {
        SeckillGoods seckillGoods = (SeckillGoods) redisTemplate.boundHashOps(RedisKey.SECKILL_GOODS + time).get(spuId);
        for (SeckillSku sku : seckillGoods.getSkuList()) {  // 获取库存
            Integer num = Integer.valueOf((String) redisTemplate.opsForValue().get(RedisKey.SECKILL_SKU_COUNT_KEY + sku.getId()));
            sku.setResidue(num);
        }
        return seckillGoods;
    }
}
