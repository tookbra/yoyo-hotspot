package com.mars.yoyo.hotspot.mifi.web;

import com.mars.yoyo.hotspot.mifi.client.SmsClient;
import com.mars.yoyo.hotspot.mifi.constant.CommonConstant;
import com.mars.yoyo.hotspot.mifi.dto.input.SmsInputDto;
import com.mars.yoyo.hotspot.mifi.service.SmsService;
import com.mars.yoyo.hotspot.result.RestResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tookbra
 * @date 2018/5/15
 * @description
 */
@Api("短信模块")
@RestController
@RequestMapping("/sms")
public class SmsController {


    @Autowired
    SmsService smsService;

    @Autowired
    SmsClient smsClient;

    /**
     * 发送验证码
     *
     * @param phone 手机号码
     * @return
     */
    @ApiOperation(value = "发送验证码", httpMethod = "post")
    @PostMapping("/captcha")
    RestResult sendCaptcha(String phone, @RequestParam(defaultValue = CommonConstant.LANG_CH) String lang) {
        if (lang.equals(CommonConstant.LANG_CH)) {
            smsService.sendCaptcha(phone, CommonConstant.SMS_TYPE_REG);
        } else {
            smsService.sendCaptcha(phone, CommonConstant.SMS_TYPE_REG_EN);
        }
        return RestResult.success("");
    }

    /**
     * 发送验证码 - app调用
     * @param phone 手机号码
     * @return
     */
    @PostMapping("/sendCaptcha")
    RestResult sendCaptcha(String phone) {
        smsService.sendCaptcha("+86" + phone, CommonConstant.SMS_TYPE_REG);
        return RestResult.success("");
    }
}
