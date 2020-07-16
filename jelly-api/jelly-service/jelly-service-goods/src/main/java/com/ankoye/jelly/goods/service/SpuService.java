package com.ankoye.jelly.goods.service;

import com.ankoye.jelly.goods.domain.Spu;
import com.ankoye.jelly.goods.model.Goods;
import com.ankoye.jelly.web.support.IService;
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
