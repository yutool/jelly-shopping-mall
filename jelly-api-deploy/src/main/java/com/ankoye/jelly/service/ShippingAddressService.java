package com.ankoye.jelly.service;


import com.ankoye.jelly.common.support.IService;
import com.ankoye.jelly.domain.ShippingAddress;

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
