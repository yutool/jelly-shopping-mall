package com.ankoye.jelly.goods.controller;

import com.ankoye.jelly.base.result.Result;
import com.ankoye.jelly.goods.domain.Spu;
import com.ankoye.jelly.goods.model.Goods;
import com.ankoye.jelly.goods.service.SpuService;
import com.ankoye.jelly.web.log.annotation.Logger;
import com.ankoye.jelly.web.log.constant.LogType;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/v1/spu")
public class SpuController {
    @Autowired
    private SpuService spuService;

    @Logger(module = "商品SPU", operation = "添加商品")
    @PostMapping
    public Result add(@RequestBody Goods goods) {
        spuService.addGoods(goods);
        return Result.success();
    }

    @Logger(module = "商品SPU", operation = "查询商品")
    @GetMapping("/{id}")
    public Result findById(@PathVariable String id) {
        Goods goods = spuService.getGoodsById(id);
        return Result.success(goods);
    }

    @Logger(module = "商品SPU", operation = "获取所有商品SPU", exclude = LogType.RESPONSE)
    @GetMapping("/list/{page}/{size}")
    public Result findSpuPage(@PathVariable Integer page, @PathVariable Integer size) {
        PageInfo<Spu> spuList = spuService.getSpuList(page, size);
        return Result.success(spuList);
    }
}
