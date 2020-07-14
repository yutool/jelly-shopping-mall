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
import java.util.LinkedList;
import java.util.List;

@Data
public class OrderModel implements Serializable {

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

    private BigDecimal postFee;

    private String remark;

    private Integer type;

    private Integer status;

    // 订单商品
    private List<OrderItem> orderItem;

    // 购物车id - 进行删除购物车
    private List<String> cartIds;

    public Order convertToOrder() {
        OrderDtoConvert orderDtoConvert = new OrderDtoConvert();
        return orderDtoConvert.convert(this);
    }

    public OrderModel convertFor(Order order) {
        OrderDtoConvert orderDtoConvert = new OrderDtoConvert();
        return orderDtoConvert.reverse().convert(order);
    }

    private static class OrderDtoConvert extends Converter<OrderModel, Order> {

        @Override
        protected Order doForward(OrderModel orderModel) {
            Order order = new Order();
            BeanUtils.copyProperties(orderModel, order);
            return order;
        }

        @Override
        protected OrderModel doBackward(Order order) {
            OrderModel orderModel = new OrderModel();
            BeanUtils.copyProperties(order, orderModel);
            return orderModel;
        }
    }

    public OrderModel() {
        orderItem = new LinkedList<>();
    }
}
