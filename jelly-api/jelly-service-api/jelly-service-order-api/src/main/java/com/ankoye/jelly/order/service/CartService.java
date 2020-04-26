package com.ankoye.jelly.order.service;

import com.ankoye.jelly.order.domian.Cart;
import com.ankoye.jelly.order.model.CartDto;

import java.util.List;

public interface CartService {

    /**
     * 添加购物车
     */
    boolean addCart(Cart cart);

    /**
     * 获取购物车商品
     */
    List<CartDto> getCartList(String id);
}
