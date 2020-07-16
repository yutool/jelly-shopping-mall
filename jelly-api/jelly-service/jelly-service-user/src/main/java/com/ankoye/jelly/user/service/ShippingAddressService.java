package com.ankoye.jelly.user.service;

import com.ankoye.jelly.user.domain.ShippingAddress;
import com.ankoye.jelly.web.support.IService;

import java.util.List;

/**
 * @author ankoye@qq.com
 */
public interface ShippingAddressService extends IService<ShippingAddress> {

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
