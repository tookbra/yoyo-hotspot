package com.mars.yoyo.hotspot.mifi.web;

import com.github.binarywang.wxpay.exception.WxPayException;
import com.mars.yoyo.hotspot.common.dps.annotation.Security;
import com.mars.yoyo.hotspot.common.dps.secutiry.UserEnv;
import com.mars.yoyo.hotspot.mifi.cache.ConfigDictionaryCache;
import com.mars.yoyo.hotspot.mifi.constant.CommonConstant;
import com.mars.yoyo.hotspot.mifi.constant.DictionaryConstant;
import com.mars.yoyo.hotspot.mifi.dto.PrePayDto;
import com.mars.yoyo.hotspot.mifi.enums.PayChannelEnum;
import com.mars.yoyo.hotspot.mifi.service.DepositService;
import com.mars.yoyo.hotspot.result.RestResult;
import com.mars.yoyo.hotspot.util.NumberUtil;
import com.paypal.base.rest.PayPalRESTException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/**
 * 押金模块
 * @author tookbra
 * @date 2018/5/21
 * @description
 */
@Slf4j
@RestController
@RequestMapping("/deposit")
public class DepositController {


    @Autowired
    DepositService depositService;

    @Autowired
    ConfigDictionaryCache configDictionaryCache;

    /**
     * 押金金额
     * @return
     */
    @GetMapping("/amount")
    RestResult getDeposit(UserEnv userEnv) {
        String value = "";
        RestResult restResult = new RestResult();
        if(userEnv.getLang().equals(CommonConstant.LANG_EN)) {
            value = configDictionaryCache.getDictionaryName(DictionaryConstant.DEPOSIT_MONEY_EN);
            restResult.setData(value);
        } else {
            value = configDictionaryCache.getDictionaryName(DictionaryConstant.DEPOSIT_MONEY_CN);
            restResult.setData(value);
        }
        return restResult;
    }

    /**
     * 支付押金, 创建订单
     * @param channel 支付渠道
     * @param path 返回路径
     * @param orderNo 订单号
     * @param userEnv 用户环境
     * @return
     */
    @PostMapping("/{channel}")
    @Security
    RestResult createOrder(@PathVariable String channel, String path,
                           @RequestParam(required = false) String orderNo,
                           UserEnv userEnv) throws WxPayException, PayPalRESTException {
        PayChannelEnum payChannelEnum = PayChannelEnum.getChannel(channel);
        PrePayDto prePayDto = depositService.createOrder(userEnv, path, orderNo, payChannelEnum);
        return RestResult.success(prePayDto);
    }
}
