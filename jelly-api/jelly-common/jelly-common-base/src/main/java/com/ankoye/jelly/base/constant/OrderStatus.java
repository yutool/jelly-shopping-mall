package com.ankoye.jelly.base.constant;

public class OrderStatus {
    /**
     * 订单关闭
     */
    public static final Integer CLOSE  = 0;
    /**
     * 订单完成
     */
    public static final Integer SUCCESS = 1;
    /**
     * 待付款
     */
    public static final Integer WAIT_PAY = 2;
    /**
     * 支付失败
     */
    public static final Integer PAY_FAIL = 3;
    /**
     * 待发货
     */
    public static final Integer WAIT_SEND = 4;
    /**
     * 已发货
     */
    public static final Integer SEND_ED = 5;
    /**
     * 待评价
     */
    public static final Integer WAIT_APPRAISE = 6;
    /**
     * 退款退货
     */
    public static final Integer REFUND  = 7;
    /**
     * 订单被删除
     */
    public static final Integer DELETE  = 99;
}
