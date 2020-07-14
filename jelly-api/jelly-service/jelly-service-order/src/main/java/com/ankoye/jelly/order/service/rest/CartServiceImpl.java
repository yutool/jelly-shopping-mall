package com.ankoye.jelly.order.service.rest;

import com.ankoye.jelly.base.result.RestResult;
import com.ankoye.jelly.goods.domain.Sku;
import com.ankoye.jelly.goods.feign.SkuFeign;
import com.ankoye.jelly.order.dao.CartMapper;
import com.ankoye.jelly.order.domian.Cart;
import com.ankoye.jelly.order.model.CartDto;
import com.ankoye.jelly.order.service.CartService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service("restCartService")
public class CartServiceImpl implements CartService {
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
            RestResult<Sku> feign = skuFeign.getSkuById(cart.getSkuId());
            Sku sku = feign.getData();
            cartDto.setSku(sku.getSku());
            cartDto.setPrice(sku.getPrice());
            cartDto.setDiscount(sku.getDiscount());
            result.add(cartDto);
        }
        return result;
    }
}
