package com.ankoye.jelly.goods.controller;

import com.ankoye.jelly.base.result.RestResult;
import com.ankoye.jelly.goods.domain.Sku;
import com.ankoye.jelly.goods.service.SkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/v1/sku")
public class SkuController {
    @Autowired
    private SkuService skuService;

    @RequestMapping("/{id}")
    public RestResult<Sku> selectById(@PathVariable String id) {
        Sku sku = skuService.getSkuById(id);
        return new RestResult<Sku>().success(sku);
    }
}
