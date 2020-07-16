package com.ankoye.jelly.order.service.impl;

import com.ankoye.jelly.goods.domain.Sku;
import com.ankoye.jelly.goods.feign.SkuFeign;
import com.ankoye.jelly.order.dao.CartMapper;
import com.ankoye.jelly.order.domian.Cart;
import com.ankoye.jelly.order.model.CartDto;
import com.ankoye.jelly.order.service.CartService;
import com.ankoye.jelly.web.support.BaseService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ankoye@qq.com
 */
@Slf4j
@Component
public class CartServiceImpl extends BaseService<Cart> implements CartService {
    @Resource
    private CartMapper cartMapper;

    @Autowired
    private SkuFeign skuFeign;

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
    public List<CartDto> getCartList(String id) {
        List<Cart> carts = cartMapper.selectList(new QueryWrapper<Cart>().eq("user_id", id));
        List<CartDto> result = new ArrayList<>();
        CartDto cartDto = null;
        for (Cart cart : carts) {   // 查询sku详情信息
            cartDto = new CartDto().convertFor(cart);
            Sku sku = skuFeign.getSkuById(cart.getSkuId()).getData();
            cartDto.setSku(sku.getSku());
            cartDto.setPrice(sku.getPrice());
            cartDto.setDiscount(sku.getDiscount());
            result.add(cartDto);
        }
        return result;
    }
}
