package com.mars.yoyo.hotspot.mifi.web;

import com.github.binarywang.wxpay.exception.WxPayException;
import com.mars.yoyo.hotspot.common.dps.annotation.Security;
import com.mars.yoyo.hotspot.common.dps.secutiry.UserEnv;
import com.mars.yoyo.hotspot.mifi.constant.CommonConstant;
import com.mars.yoyo.hotspot.mifi.dto.PrePayDto;
import com.mars.yoyo.hotspot.mifi.enums.ClientTypeEnum;
import com.mars.yoyo.hotspot.mifi.enums.PayChannelEnum;
import com.mars.yoyo.hotspot.mifi.service.RechargeService;
import com.mars.yoyo.hotspot.result.RestResult;
import com.paypal.base.rest.PayPalRESTException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/**
 * @author tookbra
 * @date 2018/6/5
 * @description
 */
@Slf4j
@RestController
@RequestMapping("/recharge")
public class RechargeController {

    @Autowired
    RechargeService rechargeService;

    @PostMapping("/{channel}")
    @Security
    public RestResult<PrePayDto> recharge(UserEnv userEnv, @RequestParam(required = false) String orderNo,
                                          @PathVariable String channel, BigDecimal amount, String path)
            throws PayPalRESTException, WxPayException {
        log.info("userEnv={}", userEnv.toString());
        PrePayDto prePayDto = rechargeService.recharge(userEnv, path, orderNo, PayChannelEnum.getChannel(channel),
                ClientTypeEnum.of(userEnv.getClientType()), amount);
        return RestResult.success(prePayDto);
    }
}
