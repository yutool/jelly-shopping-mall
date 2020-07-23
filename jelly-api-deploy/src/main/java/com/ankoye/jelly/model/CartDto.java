package com.ankoye.jelly.model;

import com.ankoye.jelly.domain.Cart;
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

    /** 原价 **/
    private BigDecimal originalPrice;

    private Integer num;

    /** JSON db **/
    private String sku;

    /** 现价 db **/
    private BigDecimal price;

    /** 折扣 - db **/
    private float discount;

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
