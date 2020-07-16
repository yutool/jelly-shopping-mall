package com.ankoye.jelly.goods.service.impl;

import com.ankoye.jelly.base.constant.GoodsStatus;
import com.ankoye.jelly.goods.dao.SkuMapper;
import com.ankoye.jelly.goods.dao.SpuMapper;
import com.ankoye.jelly.goods.domain.Sku;
import com.ankoye.jelly.goods.domain.Spu;
import com.ankoye.jelly.goods.model.Goods;
import com.ankoye.jelly.goods.reference.SpuReference;
import com.ankoye.jelly.goods.service.SpuService;
import com.ankoye.jelly.util.IdUtils;
import com.ankoye.jelly.web.support.BaseService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author ankoye@qq.com
 */
@Slf4j
@Service
@Primary
public class SpuServiceImpl extends BaseService<Spu> implements SpuService, SpuReference {
    @Resource
    private SpuMapper spuMapper;
    @Resource
    private SkuMapper skuMapper;

    @Override
    public Map<Integer, List<Spu>> getSpuByMenus(List<Integer> menus) {
        Map<Integer, List<Spu>> result = new LinkedHashMap<>();
        for (Integer menuId : menus) {
            List<Spu> spus = spuMapper.selectList(new QueryWrapper<Spu>()
                    .eq("category1_id", menuId)
                    .last("limit 0, 20")
            );
            result.put(menuId, spus);
        }
        return result;
    }

    @Override
    public Spu getSpu(String id) {
        return spuMapper.selectById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addGoods(Goods goods) {
        // 设置 SPU属性
        Spu spu = goods.getSpu();
        List<Sku> skuList = goods.getSku();

        // 设置 spu
        String spuId = IdUtils.getSpuId();
        spu.setId(spuId);
        spu.setMerchantId("0");     // 商家id
        spu.setSaleNum(0);          // 销售量
        spu.setCommentNum(0);       // 评论数
        spu.setSeq(0);              // 排序
        spu.setIsMarketable(true);  // 上架
        spu.setStatus(GoodsStatus.SUCCESS); // 0-成功
        BigDecimal price = skuList.stream().min(Comparator.comparing(Sku::getPrice)).get().getPrice();
        spu.setPrice(price);        // 设置最小的sku金额
        spuMapper.insert(spu);

        // 设置 SKU
        Date nowTime = new Date();
        for(Sku sku : skuList) {
            sku.setId(IdUtils.getSkuId());
            sku.setSpuId(spuId);    // spuId
            sku.setDiscount(10f);   // 默认不打折
            sku.setSaleNum(0);      // 销售量
            sku.setImage(spu.getPicture());     /** 暂时 */
            sku.setCreateTime(nowTime);
            sku.setUpdateTime(nowTime);
            skuMapper.insert(sku);
        }
        return true;
    }

    @Override
    public PageInfo<Spu> getSpuList(Integer page, Integer size) {
        PageHelper.startPage(page, size);
        List<Spu> spuList = spuMapper.selectList(new QueryWrapper<Spu>()
            .eq("merchant_id", 0)
        );
        return new PageInfo<>(spuList);
    }

    @Override
    public Goods getGoodsById(String id) {
        Spu spu = spuMapper.selectById(id);
        List<Sku> skuList = skuMapper.selectList(new QueryWrapper<Sku>().eq("spu_id", id));
        return new Goods(spu, skuList);
    }
}
