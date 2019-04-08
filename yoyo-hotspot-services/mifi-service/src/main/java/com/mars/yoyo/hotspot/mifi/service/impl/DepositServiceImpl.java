package com.mars.yoyo.hotspot.mifi.service.impl;

import com.github.binarywang.wxpay.exception.WxPayException;
import com.mars.yoyo.hotspot.common.dps.secutiry.UserEnv;
import com.mars.yoyo.hotspot.exception.BizFeignException;
import com.mars.yoyo.hotspot.mifi.cache.ConfigDictionaryCache;
import com.mars.yoyo.hotspot.mifi.constant.CommonConstant;
import com.mars.yoyo.hotspot.mifi.constant.DictionaryConstant;
import com.mars.yoyo.hotspot.mifi.domain.PayOrder;
import com.mars.yoyo.hotspot.mifi.domain.User;
import com.mars.yoyo.hotspot.mifi.dto.PrePayDto;
import com.mars.yoyo.hotspot.mifi.enums.ClientTypeEnum;
import com.mars.yoyo.hotspot.mifi.enums.PayChannelEnum;
import com.mars.yoyo.hotspot.mifi.service.DepositService;
import com.mars.yoyo.hotspot.mifi.service.OrderService;
import com.mars.yoyo.hotspot.mifi.service.UserService;
import com.mars.yoyo.hotspot.util.SeqUtil;
import com.paypal.base.rest.PayPalRESTException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;


/**
 * @author tookbra
 * @date 2018/5/21
 * @description
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class, readOnly = true)
public class DepositServiceImpl implements DepositService {

    @Autowired
    OrderService orderService;

    @Autowired
    ConfigDictionaryCache configDictionaryCache;

    @Autowired
    UserService userService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PrePayDto createOrder(UserEnv userEnv, String path,
                                 String orderNo, PayChannelEnum payChannelEnum) throws WxPayException, PayPalRESTException {
        User user = userService.getByUserId(userEnv.getUserId());
        if(user.getDeposited()) {
            log.info("user has payed deposit. userId={}", userEnv.getUserId());
            throw new BizFeignException("biz.deposited");
        }

        if(StringUtils.isNotBlank(orderNo)) {
            boolean flag = orderService.findByOrderNoQuery(orderNo);
            if(flag) {
                PrePayDto prePayDto = new PrePayDto();
                prePayDto.setPayResult(true);
                return prePayDto;
            }
        }

        orderNo = SeqUtil.generatorId(userEnv.getUserId());

        PayOrder payOrder = new PayOrder();
        payOrder.setOrderId(orderNo);
        payOrder.setUserId(userEnv.getUserId());
        payOrder.setPayChannel(payChannelEnum.getCode());
        payOrder.setOrderType(CommonConstant.ORDER_TYPE_DEPOSIT);

        String moneyStr = "";
        if(payChannelEnum == PayChannelEnum.PAYPAL) {
            payOrder.setCurrency("usd");
            payOrder.setBody("I'm Fish MeFi deposit");
            moneyStr = configDictionaryCache.getDictionaryName(DictionaryConstant.DEPOSIT_MONEY_EN);
        } else {
            payOrder.setCurrency("cny");
            payOrder.setBody("我是一条鱼MeFi-押金");
            moneyStr = configDictionaryCache.getDictionaryName(DictionaryConstant.DEPOSIT_MONEY_CN);
            if(user.getEn()) {
                moneyStr = configDictionaryCache.getDictionaryName(DictionaryConstant.DEPOSIT_MONEY_EN);
            }
        }
        BigDecimal money = new BigDecimal(moneyStr);
        if (user.getEn() && payChannelEnum == PayChannelEnum.WECHAT_PAY) {
            payOrder.setAmount(money.multiply(new BigDecimal(7)));
        } else {
            payOrder.setAmount(money);
        }
        payOrder.setPayIp(userEnv.getRequestIp());

        PrePayDto prePayDto = orderService.payAdpater(ClientTypeEnum.of(userEnv.getClientType()), path, payChannelEnum, payOrder);
        // todo test
//        if(prePayDto.getPayChannel().equals(PayChannelEnum.WECHAT_PAY.getChannel())) {
//            orderService.notify(payOrder.getPaymentId());
//        }

        return prePayDto;
    }
}
