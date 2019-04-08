package com.mars.yoyo.hotspot.mifi.service;

import com.github.binarywang.wxpay.exception.WxPayException;
import com.mars.yoyo.hotspot.common.dps.secutiry.UserEnv;
import com.mars.yoyo.hotspot.mifi.domain.UserAccount;
import com.mars.yoyo.hotspot.mifi.dto.PrePayDto;
import com.mars.yoyo.hotspot.mifi.enums.ClientTypeEnum;
import com.mars.yoyo.hotspot.mifi.enums.PayChannelEnum;
import com.paypal.base.rest.PayPalRESTException;

import java.math.BigDecimal;

/**
 * @author tookbra
 * @date 2018/6/5
 * @description
 */
public interface RechargeService {

    /**
     * 充值
     * @param userEnv 用户还款
     * @param orderNo 订单号
     * @param payChannelEnum 充值渠道
     * @param clientTypeEnum 客户端类型
     * @param amount 充值金额 元
     * @return PrePayDto
     */
    PrePayDto recharge(UserEnv userEnv, String path, String orderNo, PayChannelEnum payChannelEnum,
                       ClientTypeEnum clientTypeEnum, BigDecimal amount) throws PayPalRESTException, WxPayException;

    /**
     *
     * @param orderNo
     */
    void preRecharge(String orderNo);

    void rechargeConfirm(String orderNo, UserAccount userAccount);
}
