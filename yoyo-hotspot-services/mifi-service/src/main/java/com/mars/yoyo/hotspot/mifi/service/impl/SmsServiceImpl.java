package com.mars.yoyo.hotspot.mifi.service.impl;

import com.mars.yoyo.hotspot.mifi.cache.SmsCaptchaCache;
import com.mars.yoyo.hotspot.mifi.client.SmsClient;
import com.mars.yoyo.hotspot.mifi.dto.input.SmsInputDto;
import com.mars.yoyo.hotspot.mifi.service.SmsService;
import com.mars.yoyo.hotspot.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author tookbra
 * @date 2018/5/16
 * @description
 */
@Slf4j
@Service
public class SmsServiceImpl implements SmsService {

    @Autowired
    SmsClient smsClient;

    @Autowired
    SmsCaptchaCache smsCaptchaCache;

    @Override
    public void sendCaptcha(String phone, String type) {
        SmsInputDto smsInputDto = new SmsInputDto();
        smsInputDto.setPhone(phone);
        smsInputDto.setType(type);
        smsClient.sendSms(smsInputDto);
    }
}
