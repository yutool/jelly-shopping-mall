package com.ankoye.jelly.order.domian;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("tb_order_item")
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

    private Long price;

    private Long money;

    private Long payMoney;

}