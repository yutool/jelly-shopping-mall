package com.ankoye.jelly.user.service;

import com.ankoye.jelly.user.domain.ShippingAddress;

import java.util.List;

public interface ShippingAddressService {

    /**
     * 查询用户的收货地址
     * @param id 用户ID
     */
    List<ShippingAddress> selectByUserId(String id);

    /**
     * 添加收货地址
     */
    ShippingAddress add(ShippingAddress form);
}
