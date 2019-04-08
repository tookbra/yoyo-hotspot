package com.mars.yoyo.hotspot.mifi.listener;

import com.mars.yoyo.hotspot.mifi.BaseTest;
import com.mars.yoyo.hotspot.mifi.constant.CommonConstant;
import com.mars.yoyo.hotspot.mifi.event.SmsEvent;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

/**
 * @author tookbra
 * @date 2018/7/16
 * @description
 */
public class RentListenerTest extends BaseTest {

    @Autowired
    RentListener rentListener;

    @Test
    public void sendSmsEventTest() throws IOException {
        SmsEvent smsEvent = new SmsEvent();
        smsEvent.setUserId(28);
        smsEvent.setType(CommonConstant.SMS_TYPE_RETURN);
        rentListener.sendSmsEvent(smsEvent);
        System.in.read();
    }
}
