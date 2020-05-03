package com.ankoye.jelly.seckill.service;

import com.ankoye.jelly.seckill.domain.SeckillGoods;

import java.util.List;

public interface SeckillGoodsService {
    /**
     * 查询所有某时间段内所有商品
     */
    List<SeckillGoods> list(String time);

    /**
     * 查询商品详情
     */
    SeckillGoods detail(String time, String goodsId);

}
