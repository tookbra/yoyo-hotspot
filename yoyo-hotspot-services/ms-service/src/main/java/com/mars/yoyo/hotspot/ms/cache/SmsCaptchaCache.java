package com.mars.yoyo.hotspot.ms.cache;

import com.mars.yoyo.hotspot.ms.constant.SmsCacheConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;


/**
 * @author tookbra
 * @date 2018/5/16
 * @description
 */
@Slf4j
@Component
public class SmsCaptchaCache {

    /**
     * cache中获取mobiles
     *
     * @param mobile
     * @return
     */
    @Cacheable(cacheNames = SmsCacheConstant.SMS_PREFIX,
            key = "T(com.mars.yoyo.hotspot.ms.constant.SmsCacheConstant).SMS_CAPTCHA_PREFIX + #p0 + ':' + #p1",
            cacheManager = "cacheManagerFiveMinute")
    public String getCaptcha(String type, String mobile) {
        return null;
    }

    /**
     *
     * @param mobile 手机号码
     * @param captcha 验证码
     * @return
     */
    @CachePut(cacheNames = SmsCacheConstant.SMS_PREFIX ,
            key = "T(com.mars.yoyo.hotspot.ms.constant.SmsCacheConstant).SMS_CAPTCHA_PREFIX + #p0 + ':' + #p1",
            cacheManager = "cacheManagerFiveMinute")
    public String putCaptcha(String type, String mobile, String captcha) {
        return captcha;
    }

}
