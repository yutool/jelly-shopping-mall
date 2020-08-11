package com.ankoye.jelly.service.impl;

import com.ankoye.jelly.common.constant.GoodsStatus;
import com.ankoye.jelly.common.constant.SeckillKey;
import com.ankoye.jelly.common.exception.CastException;
import com.ankoye.jelly.common.support.BaseService;
import com.ankoye.jelly.dao.SeckillSkuMapper;
import com.ankoye.jelly.domain.OrderItem;
import com.ankoye.jelly.domain.SeckillSku;
import com.ankoye.jelly.domain.Sku;
import com.ankoye.jelly.model.OrderModel;
import com.ankoye.jelly.model.SeckillGoods;
import com.ankoye.jelly.service.SeckillSkuService;
import com.ankoye.jelly.service.reference.SeckillSkuReference;
import com.ankoye.jelly.service.reference.SkuReference;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;


/**
 * @author ankoye@qq.com
 */
@Service
@Primary
public class SeckillSkuServiceImpl extends BaseService<SeckillSku> implements SeckillSkuService, SeckillSkuReference {
    @Autowired
    private SkuReference skuReference;
    @Resource
    private SeckillSkuMapper seckillSkuMapper;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public PageInfo<SeckillSku> list(Integer page, Integer size) {
        PageHelper.startPage(page, size);
        List<SeckillSku> seckillGoods = seckillSkuMapper.selectList(null);
        return new PageInfo<>(seckillGoods);
    }

    @Override
    @Transactional(rollbackFor = Exception.class) // 替换全局事务
    public boolean add(SeckillSku goods) {
        // 查看已经是秒杀商品
        if(seckillSkuMapper.selectById(goods.getId()) != null) {
            CastException.cast("该商品已参加秒杀");
        }
        // 查询商品信息
        Sku sku = skuReference.selectById(goods.getId());
        // 添加秒杀商品
        goods.setSpuId(sku.getSpuId());
        goods.setSku(sku.getSku());
        goods.setImage(sku.getImage());
        goods.setResidue(goods.getNum());
        goods.setCreateTime(new Date());
        goods.setIsMarketable(true);
        goods.setStatus(GoodsStatus.SUCCESS);
        seckillSkuMapper.insert(goods);
        // 冻结商品库存
        skuReference.freezeScore(goods.getId(), goods.getNum());
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

    @Override
    public Integer updateStock(String id, Integer num) {
        return seckillSkuMapper.deductInventory(id, num);
    }

    @Override
    public void rollback(OrderModel order) {
        String userId = order.getUserId();
        // 回滚库存，删除排队状态
        for (OrderItem item : order.getOrderItem()) {
            redisTemplate.opsForValue().increment(SeckillKey.SKU_COUNT_PRE + item.getSkuId(), item.getNum());
            redisTemplate.boundHashOps(SeckillKey.USER_QUEUE).delete(userId + item.getSkuId());
        }
    }

}
