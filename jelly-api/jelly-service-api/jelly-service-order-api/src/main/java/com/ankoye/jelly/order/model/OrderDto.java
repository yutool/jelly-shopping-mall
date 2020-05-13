package com.ankoye.jelly.order.model;

import com.ankoye.jelly.order.domian.Order;
import com.ankoye.jelly.order.domian.OrderItem;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.base.Converter;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class OrderDto implements Serializable {

    private String id;

    // private String orderSn;

    private String userId;

    private BigDecimal money;

    private BigDecimal payMoney;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updateTime;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date payTime;

    private Integer addressId;

    private Integer weight;

    private Long postFee;

    private String remark;

    private Integer status;

    private List<OrderItem> orderItem;

    public Order convertToOrder() {
        OrderDtoConvert orderDtoConvert = new OrderDtoConvert();
        return orderDtoConvert.convert(this);
    }

    public OrderDto convertFor(Order order) {
        OrderDtoConvert orderDtoConvert = new OrderDtoConvert();
        return orderDtoConvert.reverse().convert(order);
    }

    private static class OrderDtoConvert extends Converter<OrderDto, Order> {

        @Override
        protected Order doForward(OrderDto orderDto) {
            Order order = new Order();
            BeanUtils.copyProperties(orderDto, order);
            return order;
        }

        @Override
        protected OrderDto doBackward(Order order) {
            OrderDto orderDto = new OrderDto();
            BeanUtils.copyProperties(order, orderDto);
            return orderDto;
        }
    }
}
