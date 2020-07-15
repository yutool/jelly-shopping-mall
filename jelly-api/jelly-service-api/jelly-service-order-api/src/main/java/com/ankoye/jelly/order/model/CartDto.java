package com.ankoye.jelly.order.model;

import com.ankoye.jelly.order.domian.Cart;
import com.google.common.base.Converter;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;

/**
 * @author ankoye@qq.com
 */
@Data
public class CartDto {

    private Long id;

    private String skuId;

    private String userId;

    private String name;

    private String image;

    private BigDecimal originalPrice;    // 原价

    private Integer num;

    private String sku;     // JSON db

    private BigDecimal price;   // 现价 db

    private float discount;     // 折扣 - db

    public Cart convertToCart() {
        CartDtoConvert cartDtoConvert = new CartDtoConvert();
        return cartDtoConvert.convert(this);
    }

    public CartDto convertFor(Cart cart) {
        CartDtoConvert cartDtoConvert = new CartDtoConvert();
        return cartDtoConvert.reverse().convert(cart);
    }

    private static class CartDtoConvert extends Converter<CartDto, Cart> {

        @Override
        protected Cart doForward(CartDto cartDto) {
            Cart cart = new Cart();
            BeanUtils.copyProperties(cartDto, cart);
            return cart;
        }

        @Override
        protected CartDto doBackward(Cart cart) {
            CartDto cartDto = new CartDto();
            BeanUtils.copyProperties(cart, cartDto);
            return cartDto;
        }
    }
}
