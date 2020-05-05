package com.ankoye.jelly.goods.service;

import com.ankoye.jelly.goods.domain.Spu;
import com.ankoye.jelly.goods.model.Goods;
import com.github.pagehelper.PageInfo;
import org.dromara.hmily.annotation.Hmily;

public interface SpuService {
    /**
     * 添加商品
     */
    boolean addGoods(Goods goods);

    /**
     * 查询商品列表
     */
    PageInfo<Spu> getSpuList(Integer page, Integer size);

    /**
     * 获取商品
     */
    Goods getGoodsById(String id);

    @Hmily
    void bdc();
}
