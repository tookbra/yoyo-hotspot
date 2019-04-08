package com.mars.yoyo.hotspot.ms.service;

import com.mars.yoyo.hotspot.ms.dto.input.SmsInputDto;
import com.mars.yoyo.hotspot.ms.dto.input.SmsReportDto;

/**
 * @author tookbra
 * @date 2018/5/15
 * @description
 */
public interface SmsService {

    /**
     * 发送短信
     * @param smsInputDto
     */
    void sendSms(SmsInputDto smsInputDto);

    /**
     * 短信状态
     * @param smsReportDto
     */
    void report(SmsReportDto smsReportDto);
}
