package com.mars.yoyo.hotspot.mifi.cache;

import com.mars.yoyo.hotspot.mifi.constant.RedisConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

/**
 * @author tookbra
 * @date 2018/6/4
 * @description
 */
@Slf4j
@Component
public class ConfigDictionaryCache {

    /**
     * 存储数据字典缓存
     *
     * @return
     */
    @CachePut(cacheNames = RedisConstant.COMMON_DICTIONARY, key = "#p0")
    public String putDictionaryName(String key, String dicName) {
        return dicName;
    }

    /**
     * 获取错误次数缓存
     *
     * @param key
     * @return
     */
    @Cacheable(cacheNames = RedisConstant.COMMON_DICTIONARY, key = "#p0")
    public String getDictionaryName(String key) {
        return null;
    }
}
