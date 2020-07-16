package com.ankoye.jelly.seckill.service;

import com.ankoye.jelly.seckill.domain.SeckillSku;
import com.ankoye.jelly.seckill.model.SeckillGoods;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author ankoye@qq.com
 */
public interface SeckillGoodsService {

    /**
     * 获取商家所有秒杀商品
     */
    PageInfo<SeckillSku> list(Integer page, Integer size);

    /**
     * 增加秒杀商品
     */
    void add(SeckillSku goods);

    /**
     * 查询所有某时间段内所有商品
     */
    List<SeckillGoods> timeList(String time);

    /**
     * 查询商品详情
     */
    SeckillGoods detail(String time, String spuId);

}
