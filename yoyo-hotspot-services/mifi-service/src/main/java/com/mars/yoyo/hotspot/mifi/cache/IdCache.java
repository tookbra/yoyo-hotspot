package com.mars.yoyo.hotspot.mifi.cache;

import com.mars.yoyo.hotspot.mifi.constant.RedisConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Component;


/**
 * @author tookbra
 * @date 2018/5/24
 * @description
 */
@Slf4j
@Component
public class IdCache {

    /**
     * 存储缓存
     *
     * @param smsCode
     * @return
     */
    @CachePut(cacheNames = RedisConstant.ORDER, key = "#p0", cacheManager = "smsCacheManager")
    public String putCaptcha(String key, String smsCode) {
        return smsCode;
    }
}
