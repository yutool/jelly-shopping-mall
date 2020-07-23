package com.ankoye.jelly.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author ankoye@qq.com
 */
@Data
@TableName("tb_order_item")
@NoArgsConstructor
public class OrderItem implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String orderId;

    private String merchantId;

    private String spuId;

    private String skuId;

    private String name;

    private String image;

    private String sku;

    private Integer num;

    private BigDecimal originalPrice;

    private BigDecimal price;

    private BigDecimal money;

    public OrderItem(String orderId, String spuId, String skuId, String merchantId, String name, String image,
                     String sku, BigDecimal originalPrice, BigDecimal price, Integer num,  BigDecimal money) {
        this.orderId = orderId;
        this.spuId = spuId;
        this.skuId = skuId;
        this.merchantId = merchantId;
        this.name = name;
        this.image = image;
        this.sku = sku;
        this.originalPrice = originalPrice;
        this.price = price;
        this.num = num;
        this.money = money;
    }

}