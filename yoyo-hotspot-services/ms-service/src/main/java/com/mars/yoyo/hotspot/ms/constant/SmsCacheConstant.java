package com.mars.yoyo.hotspot.ms.constant;

/**
 * @author tookbra
 * @date 2018/5/16
 * @description
 */
public class SmsCacheConstant {

    public static final String SMS_PREFIX = "sms";
    public static final String SMS_CAPTCHA_PREFIX = "captcha:";
    public static final String SMS_COUNT_PREFIX = "sms:count";
    public static final String SMS_TEMPLATE_PREFIX = "sms:template:";

    /**
     *
     * @param phone
     * @return
     */
    public static String getSmsCount(String phone) {
        return SMS_COUNT_PREFIX + ":" + phone;
    }

}
