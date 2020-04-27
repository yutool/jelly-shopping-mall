package com.ankoye.jelly.order.controller;

import com.alibaba.fastjson.JSON;
import com.ankoye.jelly.base.result.Result;
import com.ankoye.jelly.pay.model.Order;
import com.ankoye.jelly.pay.service.WXPayService;
import com.github.wxpay.sdk.WXPayUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/api/v1/pay/wx")
/**
 * 暂时先放在Order服务，未来抽取成一个微服务
 */
public class WXPayController {

    @Autowired
    private WXPayService wxPayService;

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    /**
     * 二维码支付
     */
    @PostMapping("/native")
    public Result<Map> nativePay(@RequestBody Order order) {
        Map resultMap = wxPayService.nativePay(order);
        return new Result<Map>().success(resultMap);
    }

    /**
     * 支付回调
     * 需要暴露给微信服务器
     */
    @PostMapping("/notify")
    public String payNotify(HttpServletRequest request) {
        try(ServletInputStream is = request.getInputStream();
            ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            log.info("回调成功");
            byte[] buffer = new byte[1024];
            int len = 0;
            while((len = is.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            String resultXml = new String(baos.toByteArray(), StandardCharsets.UTF_8);
            Map<String, String> resultMap = WXPayUtil.xmlToMap(resultXml);
            // 发送MQ，处理订单状态
            rocketMQTemplate.convertAndSend("wx-pay-notify", JSON.toJSONString(resultMap));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
    }

    /**
     * 查询订单
     * 当商户后台、网络、服务器等出现异常，商户系统最终未接收到支付通知；
     * 调用支付接口后，返回系统错误或未知交易状态情况；
     * 调用付款码支付API，返回USERPAYING的状态；
     * 调用关单或撤销接口API之前，需确认支付状态。
     */
    @GetMapping("/query/{id}")
    public Result<Map> queryOrder(@PathVariable String id) {
        Map resultMap = wxPayService.queryOrder(id);
        return new Result<Map>().success(resultMap);
    }

    /**
     * 关闭支付
     * 商户订单支付失败需要生成新单号重新发起支付，要对原订单号调用关单，避免重复支付；
     * 系统下单后，用户支付超时，系统退出不再受理，避免用户继续，请调用关单接口。
     */
    @GetMapping("/close/{id}")
    public Result<Map> closeOrder(@PathVariable String id) {
        Map resultMap = wxPayService.closeOrder(id);
        return new Result<Map>().success(resultMap);
    }

    /**
     * 待修改
     */
    @PostMapping("/refund")
    public Result refund(@RequestBody Map<String, String> map) {
        wxPayService.refund(map);
        return Result.success();
    }

}
