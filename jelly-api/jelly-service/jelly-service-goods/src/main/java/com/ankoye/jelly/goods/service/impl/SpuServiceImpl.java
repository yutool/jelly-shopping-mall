package com.ankoye.jelly.goods.service.impl;

import com.ankoye.jelly.base.constant.GoodsStatus;
import com.ankoye.jelly.goods.dao.SkuMapper;
import com.ankoye.jelly.goods.dao.SpuMapper;
import com.ankoye.jelly.goods.domain.Sku;
import com.ankoye.jelly.goods.domain.Spu;
import com.ankoye.jelly.goods.model.Goods;
import com.ankoye.jelly.goods.service.SpuService;
import com.ankoye.jelly.util.IdUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hmily.annotation.Hmily;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Slf4j
//@Service
//@Component
@Service("spuService")
public class SpuServiceImpl implements SpuService {
    @Resource
    private SpuMapper spuMapper;
    @Resource
    private SkuMapper skuMapper;

    @Override
    @Transactional
    public boolean addGoods(Goods goods) {
        // 设置 SPU属性
        Spu spu = goods.getSpu();
        String spuId = IdUtils.getSpuId();
        spu.setId(spuId);
        spu.setMerchantId(0);   // 商家id
        spu.setSaleNum(0);      // 销售量
        spu.setCommentNum(0);   // 评论数
        spu.setSeq(0);          // 排序
        spu.setIsMarketable(true);          // 上架
        spu.setStatus(GoodsStatus.SUCCESS); // 0-成功
        log.info(spu.toString());
        spuMapper.insert(spu);
        // 设置 SKU
        List<Sku> skuList = goods.getSku();
        Date nowTime = new Date();
        for(Sku sku : skuList) {
            sku.setId(IdUtils.getSkuId());
            sku.setSpuId(spuId);    // spuId
            sku.setDiscount(10f);   // 默认不打折
            sku.setSaleNum(0);      // 销售量
            sku.setCreateTime(nowTime);
            sku.setUpdateTime(nowTime);
            skuMapper.insert(sku);
        }
        return true;
    }

    @Override
    public PageInfo<Spu> getSpuList(Integer page, Integer size) {
        PageHelper.startPage(page, size);
        List<Spu> spuList = spuMapper.selectList(null);
        return new PageInfo<>(spuList);
    }

    @Override
    public Goods getGoodsById(String id) {
        Spu spu = spuMapper.selectById(id);
        List<Sku> skuList = skuMapper.selectList(new QueryWrapper<Sku>().eq("spu_id", id));
        return new Goods(spu, skuList);
    }

    @Override
    @Hmily(confirmMethod = "confirmNested", cancelMethod = "cancelNested")
    public void bdc() {
        System.out.println("try bdc");
        //CastException.cast("aa");
    }
    public void confirmNested() {
        System.out.println("bdc confirmNested");
    }

    public void cancelNested() {
        System.out.println("bdc cancelNested");
    }
}
