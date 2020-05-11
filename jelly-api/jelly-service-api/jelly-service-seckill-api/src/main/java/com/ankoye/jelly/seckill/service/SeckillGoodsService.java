package com.ankoye.jelly.seckill.service;

import com.ankoye.jelly.seckill.domain.SeckillGoods;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface SeckillGoodsService {

    /**
     * 获取商家所有秒杀商品
     */
    PageInfo<SeckillGoods> list(Integer page, Integer size);

    /**
     * 增加秒杀商品
     */
    void add(SeckillGoods goods);

    /**
     * 查询所有某时间段内所有商品
     */
    List<SeckillGoods> timeList(String time);

    /**
     * 查询商品详情
     */
    SeckillGoods detail(String time, String goodsId);

}
