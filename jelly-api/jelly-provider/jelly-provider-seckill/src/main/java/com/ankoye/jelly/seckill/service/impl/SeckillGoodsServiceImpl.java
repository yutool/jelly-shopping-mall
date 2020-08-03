package com.ankoye.jelly.seckill.service.impl;

import com.ankoye.jelly.base.constant.GoodsStatus;
import com.ankoye.jelly.goods.domain.Sku;
import com.ankoye.jelly.goods.feign.SkuFeign;
import com.ankoye.jelly.seckill.common.constant.SeckillKey;
import com.ankoye.jelly.seckill.dao.SeckillGoodsMapper;
import com.ankoye.jelly.seckill.domain.SeckillSku;
import com.ankoye.jelly.seckill.model.SeckillGoods;
import com.ankoye.jelly.seckill.service.SeckillGoodsService;
import com.ankoye.jelly.web.exception.CastException;
import com.ankoye.jelly.web.support.BaseService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * @author ankoye@qq.com
 */
@Service
@Primary
public class SeckillGoodsServiceImpl extends BaseService<SeckillSku> implements SeckillGoodsService {
    @Autowired
    private SkuFeign skuFeign;
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
    @Transactional(rollbackFor = Exception.class) // 替换全局事务
    public boolean add(SeckillSku goods) {
        // 查看已经是秒杀商品
        if(seckillGoodsMapper.selectById(goods.getId()) != null) {
            CastException.cast("该商品已参加秒杀");
        }
        // 查询商品信息
        Sku sku = skuFeign.getById(goods.getId()).getData();
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
        skuFeign.freezeScore(goods.getId(), goods.getNum());
        return true;
    }

    @Override
    public List<SeckillGoods> timeList(String time) {
        List<SeckillGoods> goodsList = new LinkedList<>();
        // 替换商品库存
        List<Object> list =  redisTemplate.boundHashOps(SeckillKey.GOODS_PRE + time).values();
        if (list == null) {
            CastException.cast("商品列表不存在");
        }
        for (Object o : list) {
            SeckillGoods seckillGoods = (SeckillGoods) o;
            for (SeckillSku sku : seckillGoods.getSkuList()) {
                String _num = (String) redisTemplate.opsForValue().get(SeckillKey.SKU_COUNT_PRE + sku.getId());
                int num = _num == null ? 0 : Integer.parseInt(_num);
                sku.setResidue(num);
            }
            goodsList.add(seckillGoods);
        }
        return goodsList;
    }

    @Override
    public SeckillGoods detail(String time, String spuId) {
        SeckillGoods seckillGoods = (SeckillGoods) redisTemplate.boundHashOps(SeckillKey.GOODS_PRE + time).get(spuId);
        if (seckillGoods == null) {
            CastException.cast("商品不存在");
        }
        for (SeckillSku sku : seckillGoods.getSkuList()) {
            String _num = (String) redisTemplate.opsForValue().get(SeckillKey.SKU_COUNT_PRE + sku.getId());
            int num = _num == null ? 0 : Integer.parseInt(_num);
            sku.setResidue(num);
        }
        return seckillGoods;
    }
}
