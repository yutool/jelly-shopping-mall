package com.ankoye.jelly.user.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ankoye@qq.com
 */
@Data
@TableName("tb_shipping_address")
public class ShippingAddress implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String userId;

    private String consignee;

    private String telephone;

    private String address;

    private String detail;

    private String postcode;

    private Boolean isDefault;
}
