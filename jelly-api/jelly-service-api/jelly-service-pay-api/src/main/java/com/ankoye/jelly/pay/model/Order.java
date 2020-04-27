package com.ankoye.jelly.pay.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Order {

    private String id;

    private String name;

    private BigDecimal money;

    // ...
}
