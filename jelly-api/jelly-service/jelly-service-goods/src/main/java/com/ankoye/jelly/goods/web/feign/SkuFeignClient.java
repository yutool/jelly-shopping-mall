package com.ankoye.jelly.goods.web.feign;

import com.ankoye.jelly.base.result.Wrapper;
import com.ankoye.jelly.base.result.Wrappers;
import com.ankoye.jelly.goods.domain.Sku;
import com.ankoye.jelly.goods.service.SkuService;
import com.ankoye.jelly.web.support.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ankoye@qq.com
 */
@CrossOrigin
@RestController
@RequestMapping("/rpc/sku")
public class SkuFeignClient extends BaseController<Sku> {
    @Autowired
    private SkuService skuService;

    @RequestMapping("/{id}")
    public Wrapper<Sku> selectById(@PathVariable String id) {
        Sku sku = skuService.getSkuById(id);
        return Wrappers.success(sku);
    }
}
