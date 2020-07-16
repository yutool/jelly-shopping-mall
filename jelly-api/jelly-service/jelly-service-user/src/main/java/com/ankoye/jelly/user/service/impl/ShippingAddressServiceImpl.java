package com.ankoye.jelly.user.service.impl;

import com.ankoye.jelly.user.domain.ShippingAddress;
import com.ankoye.jelly.user.mapper.ShippingAddressMapper;
import com.ankoye.jelly.user.service.ShippingAddressService;
import com.ankoye.jelly.web.support.BaseService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ankoye@qq.com
 */
@Service
@Primary
public class ShippingAddressServiceImpl extends BaseService<ShippingAddress> implements ShippingAddressService {
    @Resource
    private ShippingAddressMapper addressMapper;

    @Override
    public List<ShippingAddress> selectByUserId(String id) {
        return addressMapper.selectList(new QueryWrapper<ShippingAddress>()
                .eq("user_id", id)
                .orderByDesc("is_default")
        );
    }

    @Override
    @Transactional
    public ShippingAddress add(ShippingAddress form) {
        // 如果还没有收货地址，那么设置该地址为默认地址
        int count = addressMapper.selectCount(new QueryWrapper<ShippingAddress>()
                .eq("user_id", form.getUserId())
        );
        if (count == 0) {
            form.setIsDefault(true);
        }
        // 如果新增的是默认地址，那么其他设置为非默认
        if (count > 0 && form.getIsDefault()) {
            ShippingAddress address = addressMapper.selectOne(new QueryWrapper<ShippingAddress>()
                    .eq("user_id", form.getUserId())
                    .eq("is_default", 1)
            );
            address.setIsDefault(false);
            addressMapper.updateById(address);
        }
        // 添加到数据库
        addressMapper.insert(form);
        return form;
    }
}
