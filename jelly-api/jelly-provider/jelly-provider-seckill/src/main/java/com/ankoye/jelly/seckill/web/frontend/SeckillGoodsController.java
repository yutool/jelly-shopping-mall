package com.ankoye.jelly.seckill.web.frontend;

import com.ankoye.jelly.base.result.Result;
import com.ankoye.jelly.seckill.domain.SeckillSku;
import com.ankoye.jelly.seckill.model.SeckillGoods;
import com.ankoye.jelly.seckill.service.SeckillGoodsService;
import com.ankoye.jelly.util.DateUtils;
import com.ankoye.jelly.web.log.annotation.Logger;
import com.ankoye.jelly.web.support.BaseController;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ankoye@qq.com
 */
@RestController
@RequestMapping("/seckill/goods")
public class SeckillGoodsController extends BaseController {

    @Autowired
    private SeckillGoodsService seckillGoodsService;

    @PostMapping
    @Logger(module = "秒杀商品", operation = "增加秒杀商品")
    public Result add(@RequestBody SeckillSku goods) {
        return handleResult(seckillGoodsService.add(goods));
    }

    /**
     * 获取商家所有参加秒杀的商品
     */
    @GetMapping("/list/{page}/{size}")
    public Result findGoodsPage(@PathVariable Integer page, @PathVariable Integer size) {
        PageInfo<SeckillSku> list = seckillGoodsService.list(page, size);
        return Result.success(list);
    }

    /**
     * 秒杀时间菜单
     */
    @GetMapping("/menus")
    public Result menus() {
        List<String> dateMenus = DateUtils.getDateMenus();
        return Result.success(dateMenus);
    }

    /**
     * 获取该时间段所有商品
     * @param time 2020050210
     */
    @GetMapping("/time_list/{time}")
    public Result list(@PathVariable String time){
        List<SeckillGoods> goodsList = seckillGoodsService.timeList(time);
        return Result.success(goodsList);
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