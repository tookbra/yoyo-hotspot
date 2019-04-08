package com.mars.yoyo.hotspot.device.cache;

import com.mars.yoyo.hotspot.device.constant.RedisConstant;
import com.mars.yoyo.hotspot.device.session.MifiSession;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;

/**
 * @author tookbra
 * @date 2018/4/17
 * @description
 */
@Component
public class SessionCache {

    /**
     * 更新缓存
     * @param session
     * @return
     */
    @Caching(
            put = {
                    @CachePut(cacheNames = RedisConstant.DEVICE_SESSION, key = "#session.deviceId"),
                    @CachePut(cacheNames = RedisConstant.DEVICE_SESSION, key = "#session.sessionId")
            }
    )
    public MifiSession putSession(MifiSession session) {
        return session;
    }

    /**
     * 获取缓存
     * @param boxId
     * @return
     */
    @Cacheable(cacheNames = RedisConstant.DEVICE_SESSION, key = "#p0")
    public MifiSession getSession(String boxId) {
        return null;
    }

    /**
     * 删除缓存
     * @param boxId
     */
    @CacheEvict(cacheNames = RedisConstant.DEVICE_SESSION, key = "#p0")
    public void removeSession(String boxId) { }
}
