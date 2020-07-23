package com.ankoye.jelly.service;


import com.ankoye.jelly.common.support.IService;
import com.ankoye.jelly.domain.Spu;
import com.ankoye.jelly.model.Goods;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * @author ankoye@qq.com
 */
public interface SpuService extends IService<Spu> {
    /**
     * 获取首页内容
     */
    Map<Integer, List<Spu>> getSpuByMenus(List<Integer> menus);

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

}
