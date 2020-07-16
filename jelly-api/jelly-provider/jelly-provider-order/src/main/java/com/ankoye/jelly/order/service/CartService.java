package com.ankoye.jelly.order.service;

import com.ankoye.jelly.order.domian.Cart;
import com.ankoye.jelly.order.model.CartDto;
import com.ankoye.jelly.web.support.IService;

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
