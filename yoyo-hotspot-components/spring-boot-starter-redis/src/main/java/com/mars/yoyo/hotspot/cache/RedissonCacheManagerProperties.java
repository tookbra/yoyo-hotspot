package com.mars.yoyo.hotspot.cache;

import lombok.Data;
import org.redisson.spring.cache.CacheConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * @author tookbra
 * @date 2018/4/17
 * @description
 */
@Data
@ConfigurationProperties(prefix = "spring.cache")
public class RedissonCacheManagerProperties {

    /**
     * 是否缓存 null 值
     */
    private boolean allowNullValues = true;
    /**
     * RedissonCache 配置
     */
    private Map<String, CacheConfig> configs = new HashMap<>();
    /**
     * 是否开启动态缓存
     */
    private boolean dynamic = true;
    /**
     * 缓存配置路径
     */
    private String configLocation;
    /**
     *  是否回滚到 NoOpCacheManager
     */
    private boolean fallbackToNoOpCache = false;
}
