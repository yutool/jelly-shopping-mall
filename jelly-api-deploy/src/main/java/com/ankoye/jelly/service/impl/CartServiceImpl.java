package com.ankoye.jelly.service.impl;

import com.ankoye.jelly.common.support.BaseService;
import com.ankoye.jelly.dao.CartMapper;
import com.ankoye.jelly.domain.Cart;
import com.ankoye.jelly.domain.Sku;
import com.ankoye.jelly.model.CartDto;
import com.ankoye.jelly.service.CartService;
import com.ankoye.jelly.service.reference.SkuReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ankoye@qq.com
 */
@Slf4j
@Primary
@Component
public class CartServiceImpl extends BaseService<Cart> implements CartService {
    @Resource
    private CartMapper cartMapper;

    @Resource
    private SkuReference skuReference;

    @Override
    public boolean addCart(Cart cart) {
        QueryWrapper<Cart> wrapper = new QueryWrapper<>();
        wrapper.eq("sku_id", cart.getSkuId());
        wrapper.eq("user_id", cart.getUserId());
        Cart dbCart = cartMapper.selectOne(wrapper);
        if(dbCart != null) {
            // 待优化 - 数量[1-库存]
            dbCart.setNum(dbCart.getNum() + cart.getNum());
            cartMapper.updateById(dbCart);
        } else {
            cartMapper.insert(cart);
        }
        return true;
    }

    @Override
    public List<CartDto> getUserCart(String id) {
        List<Cart> carts = cartMapper.selectList(new QueryWrapper<Cart>().eq("user_id", id));
        List<CartDto> result = new ArrayList<>();
        CartDto cartDto = null;
        for (Cart cart : carts) {   // 查询sku详情信息
            cartDto = new CartDto().convertFor(cart);
            Sku sku = skuReference.selectById(cart.getSkuId());
            cartDto.setSku(sku.getSku());
            cartDto.setPrice(sku.getPrice());
            cartDto.setDiscount(sku.getDiscount());
            result.add(cartDto);
        }
        return result;
    }
}
