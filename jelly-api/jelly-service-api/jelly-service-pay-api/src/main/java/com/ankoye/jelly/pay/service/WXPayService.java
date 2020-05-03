package com.ankoye.jelly.pay.service;

import com.ankoye.jelly.pay.model.Order;

import java.util.Map;

public interface WXPayService {

    /**
     * 二维码状态
     */
    Map<String, String> nativePay(Order order, String attach);

    /**
     * 查询支付状态
     */
    Map<String, String> queryOrder(String orderId);

    /**
     * 关闭支付
     */
    Map<String, String> closeOrder(String orderId);

    /**
     * 退款
     */
    String refund(Map<String, String> map);
}
