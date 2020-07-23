package com.ankoye.jelly.service;

import com.ankoye.jelly.model.PayOrder;

import java.util.Map;

/**
 * @author ankoye@qq.com
 */
public interface WXPayService {

    /**
     * 二维码状态
     */
    Map<String, String> nativePay(PayOrder order);

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
