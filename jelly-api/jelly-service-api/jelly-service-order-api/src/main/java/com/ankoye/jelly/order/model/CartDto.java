package com.ankoye.jelly.order.model;

import com.ankoye.jelly.goods.domain.Sku;
import com.ankoye.jelly.order.domian.Cart;
import com.google.common.base.Converter;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class CartDto {

    private Long id;

    private Sku sku;

    private String userId;

    private String name;

    private Long price;

    private Integer num;

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
            cart.setSkuId(cartDto.getSku().getId());
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
