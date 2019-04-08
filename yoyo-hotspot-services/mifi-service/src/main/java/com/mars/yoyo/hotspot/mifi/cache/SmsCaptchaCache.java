package com.mars.yoyo.hotspot.mifi.cache;

import com.mars.yoyo.hotspot.mifi.constant.SmsCacheConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
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
            key = "T(com.mars.yoyo.hotspot.mifi.constant.SmsCacheConstant).SMS_CAPTCHA_PREFIX + #p0 + ':' + #p1",
            cacheManager = "cacheManagerFiveMinute")
    public String getCaptcha(String type, String mobile) {
        return null;
    }

    /**
     * 缓存中删除
     *
     * @param
     */
    @CacheEvict(cacheNames = SmsCacheConstant.SMS_PREFIX,
            key = "T(com.mars.yoyo.hotspot.mifi.constant.SmsCacheConstant).SMS_CAPTCHA_PREFIX + #p0",
            cacheManager = "cacheManagerFiveMinute")
    public void removeCaptcha(String key) {
        log.info("delete sms captcha from cache, key={} ", key);
    }
}
