package com.mars.yoyo.hotspot.ms.controller;

import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.mars.yoyo.hotspot.ms.dto.input.SmsInputDto;
import com.mars.yoyo.hotspot.ms.dto.input.SmsReportDto;
import com.mars.yoyo.hotspot.ms.enums.SmsTypeEnum;
import com.mars.yoyo.hotspot.ms.service.SmsService;
import com.mars.yoyo.hotspot.result.RestResult;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

    /**
     * 发送短信
     * @param smsInputDto {@link SmsInputDto}
     * @return {@link RestResult}
     */
    @PostMapping
    RestResult sendSms(@RequestBody @Valid SmsInputDto smsInputDto, BindingResult result) {
        try {
            PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
            Phonenumber.PhoneNumber number = phoneUtil.parseAndKeepRawInput(smsInputDto.getPhone(), "");
            smsInputDto.setPhonePre(String.valueOf(number.getCountryCode()));
            smsInputDto.setPhone(String.valueOf(number.getNationalNumber()));
            String region = phoneUtil.getRegionCodeForNumber(number);
        } catch (Exception e) {
            e.printStackTrace();
            return RestResult.error("");
        }
        smsService.sendSms(smsInputDto);
        return RestResult.success("");
    }

    /**
     * 接收短信报告
     * @param smsReportDto {@link SmsReportDto}
     * @return
     */
    @PostMapping("/report")
    String receiveReport(SmsReportDto smsReportDto) {
        smsService.report(smsReportDto);
        return "ok";
    }
}
