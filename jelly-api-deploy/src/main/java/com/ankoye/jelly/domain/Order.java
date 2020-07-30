package com.ankoye.jelly.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author ankoye@qq.com
 */
@Data
@TableName("tb_order")
public class Order implements Serializable {
    @TableId
    private String id;

    // private String orderSn;

    private String userId;

    private BigDecimal money;

    private BigDecimal payMoney;

    private Date createTime;

    private Date updateTime;

    private Date payTime;

    private String transactionId;

    private Integer addressId;

    private Integer weight;

    private Long postFee;

    private String remark;

    // 订单类型 1 - 秒杀订单
    private Integer type;

    private Integer status;

}