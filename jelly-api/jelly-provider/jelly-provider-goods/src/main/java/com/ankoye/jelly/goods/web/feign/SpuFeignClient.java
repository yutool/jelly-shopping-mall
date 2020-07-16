package com.ankoye.jelly.goods.web.feign;

import com.ankoye.jelly.base.result.Wrapper;
import com.ankoye.jelly.base.result.Wrappers;
import com.ankoye.jelly.goods.domain.Spu;
import com.ankoye.jelly.goods.feign.SpuFeign;
import com.ankoye.jelly.goods.reference.SpuReference;
import com.ankoye.jelly.goods.service.SpuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Admin
 */
@RestController
@RequestMapping("/spu")
public class SpuFeignClient implements SpuFeign {

    @Autowired
    private SpuReference spuReference;

    @Override
    public Wrapper<Spu> getSpuById(@PathVariable String id) {
        Spu spu = spuReference.selectById(id);
        return Wrappers.success(spu);
    }
}
