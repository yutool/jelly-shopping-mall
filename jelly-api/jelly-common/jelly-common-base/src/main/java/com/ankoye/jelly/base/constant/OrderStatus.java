package com.ankoye.jelly.base.constant;

public class OrderStatus {
    /**
     * 订单完成
     */
    public static final Integer SUCCESS = 0;
    /**
     * 待付款
     */
    public static final Integer WAIT_PAY = 1;
    /**
     * 支付失败
     */
    public static final Integer PAY_FAIL = 2;
    /**
     * 待发货
     */
    public static final Integer WAIT_SEND = 3;
    /**
     * 待评价
     */
    public static final Integer WAIT_APPRAISE = 4;
    /**
     * 退款退货
     */
    public static final Integer REFUND  = 5;

    /**
     * 订单关闭
     */
    public static final Integer CLOSE  = 6;
}
