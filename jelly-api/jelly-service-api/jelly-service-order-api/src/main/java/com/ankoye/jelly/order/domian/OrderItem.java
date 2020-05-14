package com.ankoye.jelly.order.domian;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@TableName("tb_order_item")
@NoArgsConstructor
public class OrderItem implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String orderId;

    private String merchantId;

    private String skuId;

    private String name;

    private String image;

    private String sku;

    private Integer num;

    private BigDecimal price;

    private BigDecimal money;

    private BigDecimal payMoney;

    public OrderItem(String orderId, String skuId, String merchantId, String name, String image, String sku,
                        BigDecimal price, Integer num, BigDecimal money, BigDecimal payMoney) {
        this.orderId = orderId;
        this.skuId = skuId;
        this.merchantId = merchantId;
        this.name = name;
        this.image = image;
        this.sku = sku;
        this.price = price;
        this.num = num;
        this.money = money;
        this.payMoney = payMoney;
    }

}