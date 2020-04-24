package com.ankoye.jelly.goods.controller;

import com.ankoye.jelly.base.result.Result;
import com.ankoye.jelly.goods.domain.Spu;
import com.ankoye.jelly.goods.model.Goods;
import com.ankoye.jelly.goods.service.SpuService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/spu")
public class SpuController {
    @Autowired
    private SpuService spuService;

    @PostMapping
    public Result addGoods(@RequestBody Goods goods) {
        spuService.addGoods(goods);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<Goods> getGoods(@PathVariable String id) {
        Goods goods = spuService.getGoodsById(id);
        return new Result<Goods>().success(goods);
    }

    @GetMapping("/list/{page}/{size}")
    public Result<PageInfo<Spu>> getSpuList(@PathVariable Integer page, @PathVariable Integer size) {
        PageInfo<Spu> spuList = spuService.getSpuList(page, size);
        return new Result<PageInfo<Spu>>().success(spuList);
    }
}
