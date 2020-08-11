package com.ankoye.jelly.seckill.web.feign;

import com.ankoye.jelly.base.result.Wrapper;
import com.ankoye.jelly.base.result.Wrappers;
import com.ankoye.jelly.order.model.OrderModel;
import com.ankoye.jelly.seckill.feign.SeckillSkuFeign;
import com.ankoye.jelly.seckill.reference.SeckillSkuReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ankoye@qq.com
 */
@RestController
public class SeckillSkuFeignCilent implements SeckillSkuFeign {
    @Autowired
    private SeckillSkuReference seckillSkuReference;

    @Override
    public Wrapper<Integer> updateStock(String id, Integer num) {
        Integer res = seckillSkuReference.updateStock(id, num);
        return Wrappers.success(res);
    }

    @Override
    public Wrapper rollback(OrderModel order) {
        seckillSkuReference.rollback(order);
        return Wrappers.success();
    }
}
