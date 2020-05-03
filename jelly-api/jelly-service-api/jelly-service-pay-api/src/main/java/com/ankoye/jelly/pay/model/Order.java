package com.ankoye.jelly.pay.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Order {

    private String id;

    private String name;

    private BigDecimal money;

    private Integer type;   // 1 - 普通订单 2 - 秒杀订单

    // ...
}
