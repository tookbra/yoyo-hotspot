package com.mars.yoyo.hotspot.ms.cache;

import com.mars.yoyo.hotspot.ms.constant.SmsCacheConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

/**
 * @author tookbra
 * @date 2018/6/2
 * @description
 */
@Slf4j
@Component
public class SmsCache {

    /**
     * cache中获取mobiles
     *
     * @param mobile
     * @return
     */
    @Cacheable(cacheNames = SmsCacheConstant.SMS_PREFIX,
            key = "T(com.mars.yoyo.hotspot.ms.constant.SmsCacheConstant).SMS_CAPTCHA_PREFIX +#p0",
            cacheManager = "cacheManagerSecond")
    public String getCaptcha(String mobile) {
        return null;
    }

    /**
     *
     * @param mobile 手机号码
     * @return
     */
    @CachePut(cacheNames = SmsCacheConstant.SMS_PREFIX,
            key = "T(com.mars.yoyo.hotspot.ms.constant.SmsCacheConstant).SMS_CAPTCHA_PREFIX +#p0",
            cacheManager = "cacheManagerSecond")
    public String putCaptcha(String mobile) {
        return mobile;
    }
}
