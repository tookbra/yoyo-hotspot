package com.mars.yoyo.hotspot.mifi.service;

import com.github.binarywang.wxpay.exception.WxPayException;
import com.mars.yoyo.hotspot.common.dps.secutiry.UserEnv;
import com.mars.yoyo.hotspot.mifi.dto.PrePayDto;
import com.mars.yoyo.hotspot.mifi.enums.PayChannelEnum;
import com.paypal.base.rest.PayPalRESTException;


/**
 * @author tookbra
 * @date 2018/5/21
 * @description
 */
public interface DepositService {

    /**
     * 支付押金
     * @param userEnv 用户环境
     * @param path 返回路径
     * @param payChannelEnum 支付渠道
     * @param orderNo 订单号
     * @return
     * @throws WxPayException
     * @throws PayPalRESTException
     * @return {@Link PrePayDto}
     */
    PrePayDto createOrder(UserEnv userEnv, String path,
                          String orderNo, PayChannelEnum payChannelEnum) throws WxPayException, PayPalRESTException;


}
