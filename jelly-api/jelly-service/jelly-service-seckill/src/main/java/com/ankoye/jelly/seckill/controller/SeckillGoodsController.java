package com.ankoye.jelly.seckill.controller;

import com.ankoye.jelly.base.result.Result;
import com.ankoye.jelly.seckill.domain.SeckillGoods;
import com.ankoye.jelly.seckill.service.SeckillGoodsService;
import com.ankoye.jelly.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/seckill/goods")
public class SeckillGoodsController {

    @Autowired
    private SeckillGoodsService seckillGoodsService;

    @GetMapping("/menus")
    public Result menus() {
        List<Date> dateMenus = DateUtils.getDateMenus();
        return Result.success(dateMenus);
    }

    /**
     * 获取该时间段所有商品
     * @param time 2020050210
     */
    @GetMapping("/list/{time}")
    public Result list(@PathVariable String time){
        List<SeckillGoods> seckillGoodsList = seckillGoodsService.list(time);
        return Result.success(seckillGoodsList);
    }

    /**
     * 根据时间和ID查询秒杀商品详情
     */
    @GetMapping("/{time}/{id}")
    public Result detail(@PathVariable String time, @PathVariable String id) {
        SeckillGoods goods = seckillGoodsService.detail(time, id);
        return Result.success(goods);
    }
}