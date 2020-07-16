package com.ankoye.jelly.goods.web.frontend;

import com.ankoye.jelly.base.result.Result;
import com.ankoye.jelly.goods.domain.Spu;
import com.ankoye.jelly.goods.model.Goods;
import com.ankoye.jelly.goods.service.SpuService;
import com.ankoye.jelly.web.log.annotation.Logger;
import com.ankoye.jelly.web.log.constant.LogType;
import com.ankoye.jelly.web.support.BaseController;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author ankoye@qq.com
 */
@CrossOrigin
@RestController
@RequestMapping("/spu")
public class SpuController extends BaseController {
    @Autowired
    private SpuService spuService;

    @Logger(module = "商品SPU", operation = "添加商品")
    @PostMapping
    public Result save(@RequestBody Goods goods) {
        return handleResult(spuService.addGoods(goods));
    }

    @Logger(module = "商品SPU", operation = "查询商品详情")
    @GetMapping("/detail/{id}")
    public Result findById(@PathVariable String id) {
        Goods goods = spuService.getGoodsById(id);
        return Result.success(goods);
    }

    /**
     * 这里需要前端修改下
     */
    @Logger(module = "商品SPU", operation = "获取所有商品SPU", exclude = LogType.RESPONSE)
    @GetMapping("/list/{page}/{size}")
    public Result findSpuPage(@PathVariable Integer page, @PathVariable Integer size) {
        PageInfo<Spu> spuList = spuService.getSpuList(page, size);
        return Result.success(spuList);
    }
}
