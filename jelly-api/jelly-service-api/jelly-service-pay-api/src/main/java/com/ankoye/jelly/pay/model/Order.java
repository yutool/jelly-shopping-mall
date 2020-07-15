package com.ankoye.jelly.pay.model;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 提交给第三方支付的订单信息，并非系统订单
 *
 * @author ankoye@qq.com
 */
@Data
public class Order {

    private String id;

    private String name;

    private BigDecimal money;

    private Integer type;   // 1 - 普通订单 2 - 秒杀订单

    // ...
}
