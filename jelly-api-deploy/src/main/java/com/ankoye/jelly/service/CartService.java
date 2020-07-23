package com.ankoye.jelly.service;


import com.ankoye.jelly.common.support.IService;
import com.ankoye.jelly.domain.Cart;
import com.ankoye.jelly.model.CartDto;

import java.util.List;

/**
 * @author ankoye@qq.com
 */
public interface CartService extends IService<Cart> {

    /**
     * 添加购物车
     */
    boolean addCart(Cart cart);

    /**
     * 获取购物车商品
     */
    List<CartDto> getUserCart(String id);
}
