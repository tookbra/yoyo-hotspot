package com.mars.yoyo.hotspot.mifi.client;

import com.mars.yoyo.hotspot.mifi.BaseTest;
import com.mars.yoyo.hotspot.mifi.dto.input.SmsInputDto;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author tookbra
 * @date 2018/5/29
 * @description
 */
public class SmsClientTest extends BaseTest {

    @Autowired
    SmsClient smsClient;


    @Test
    public void sendSmsTest() {
        SmsInputDto smsInputDto = new SmsInputDto();
        smsClient.sendSms(smsInputDto);
    }
}
