package com.ankoye.jelly.order.domian;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("tb_order")
public class Order implements Serializable {
    @TableId
    private String id;

    // private String orderSn;

    private Long userId;

    private Long money;

    private Long payMoney;

    private Date createTime;

    private Date payTime;

    private Integer addressId;

    private Integer weight;

    private Long postFee;

    private String remark;

    private Integer status;

}