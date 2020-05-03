package com.ankoye.jelly.seckill.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("tb_seckill_order")
public class SeckillOrder implements Serializable {
    @TableId
    private String id;

    /**
     * 秒杀商品ID
     */
    private String skuId;

    /**
     * 支付金额
     */
    private BigDecimal money;

    /**
     * 用户
     */
    private String userId;

    /**
     * 商家
     */
    private String merchantId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 支付时间
     */
    private Date payTime;

    /**
     * 收货人地址
     */
    private String addressId;

    /**
     * 交易流水
     */
    private String transactionId;

    /**
     * 状态
     */
    private Integer status;
}