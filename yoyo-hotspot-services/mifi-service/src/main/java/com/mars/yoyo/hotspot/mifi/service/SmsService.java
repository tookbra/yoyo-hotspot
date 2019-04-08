package com.mars.yoyo.hotspot.mifi.service;

/**
 * @author tookbra
 * @date 2018/5/16
 * @description
 */
public interface SmsService {

    /**
     * 发送验证码
     * @param phone 手机号码
     * @param type 短信类型
     */
    void sendCaptcha(String phone, String type);
}
