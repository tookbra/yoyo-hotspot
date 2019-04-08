package com.mars.yoyo.hotspot.auth.cache;

import com.mars.yoyo.hotspot.auth.constatns.RedisConstants;
import com.mars.yoyo.hotspot.auth.domain.UserAuth;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

/**
 * @author tookbra
 * @date 2018/5/25
 * @description
 */
@Component
public class AuthTokenCache {

    /**
     * 更新token缓存
     *
     * @param userAuth
     * @return
     */
    @CachePut(cacheNames = RedisConstants.USER_TOKEN, key = "#p0.userId", cacheManager = "days30CacheManager")
    public UserAuth putUserToken(UserAuth userAuth) {
        return userAuth;
    }

    /**
     * 获取token缓存
     *
     * @param      * @param token 用户id
     * @return
     */
    @Cacheable(cacheNames = RedisConstants.USER_TOKEN, key = "#p0.userId", cacheManager = "days30CacheManager")
    public UserAuth getUserToken(UserAuth userAuth) {
        return null;
    }

    /**
     * 获取token缓存
     *
     * @param      * @param token 用户id
     * @return
     */
    @Cacheable(cacheNames = RedisConstants.USER_TOKEN, key = "#p0", cacheManager = "days30CacheManager")
    public UserAuth getUserToken(String userId) {
        return null;
    }
}
