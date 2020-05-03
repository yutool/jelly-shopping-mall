package com.ankoye.jelly.seckill.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户排队抢单信息
 */
@Data

public class OrderQueue implements Serializable {
    public static final int BE_QUEUING = 1; // 排队中
    public static final int BE_FAIL = 2; // 排队失败
    public static final int CREATED = 3;    // 订单已创建
    // 用户
    private String userId;

    // 排队时间
    private Date createTime;

    // 商品id
    private String goodsId;

    // 时间段
    private String time;

    // 排队状态
    private Integer status;

    // 订单号
    private String orderId;

    public OrderQueue(String userId, Date createTime, String time, String goodsId, Integer status) {
        this.userId = userId;
        this.createTime = createTime;
        this.time = time;
        this.goodsId = goodsId;
        this.status = status;
    }
}
