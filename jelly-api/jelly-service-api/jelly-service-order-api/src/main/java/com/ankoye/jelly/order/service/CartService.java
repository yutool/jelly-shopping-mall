package com.ankoye.jelly.order.service;

import com.ankoye.jelly.order.domian.Cart;
import com.ankoye.jelly.order.model.CartDto;
import com.github.pagehelper.PageInfo;

public interface CartService {

    /**
     * 添加购物车
     */
    boolean addCart(Cart cart);

    /**
     * 获取购物车商品
     */
    PageInfo<CartDto> getCartList(String id, Integer page, Integer size);
}
