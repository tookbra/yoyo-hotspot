package com.mars.yoyo.hotspot.mifi.web;

import com.github.binarywang.wxpay.exception.WxPayException;
import com.mars.yoyo.hotspot.common.dps.annotation.Security;
import com.mars.yoyo.hotspot.common.dps.secutiry.UserEnv;
import com.mars.yoyo.hotspot.mifi.service.OrderService;
import com.mars.yoyo.hotspot.mifi.vo.OrderConfirmVo;
import com.mars.yoyo.hotspot.result.RestResult;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 订单模块
 * @author tookbra
 * @date 2018/5/22
 * @description
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    /**
     * 订单确认
     * @param paymentId
     * @param payerId
     * @return
     * @throws PayPalRESTException
     */
    @PostMapping("/pay")
    @Security
    RestResult pay(String paymentId, String payerId) throws PayPalRESTException {
        orderService.orderConfirm(paymentId, payerId);
        return RestResult.success("");
    }

    /**
     * 支付确认
     * @param orderNo 第三方支付id
     * @return
     */
    @PostMapping("/confirm")
    @Security
    RestResult<OrderConfirmVo> confirm(UserEnv userEnv, @RequestParam(required = true) String orderNo) throws WxPayException {
        OrderConfirmVo orderConfirmVo = orderService.orderConfirm(orderNo);
        return RestResult.success(orderConfirmVo);
    }
}
