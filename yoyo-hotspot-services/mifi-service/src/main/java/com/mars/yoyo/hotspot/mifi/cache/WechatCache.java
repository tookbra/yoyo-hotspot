package com.mars.yoyo.hotspot.mifi.cache;

import com.mars.yoyo.hotspot.mifi.constant.RedisConstant;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

/**
 * @author tookbra
 * @date 2018/4/19
 * @description
 */
@Component
public class WechatCache {

    /**
     * 更新缓存
     * @param token
     * @return
     */
    @CachePut(cacheNames = RedisConstant.WECHAT_TOKEN, key = "#p0", cacheManager = "days30CacheManager")
    public WxMpOAuth2AccessToken putToken(String code, WxMpOAuth2AccessToken token) {
        return token;
    }

    @CachePut(cacheNames = RedisConstant.WECHAT_USER, key = "#p0", cacheManager = "days30CacheManager")
    public WxMpUser putUser(String code, WxMpUser user) {
        return user;
    }

    /**
     * 获取缓存
     * @param code
     * @return
     */
    @Cacheable(cacheNames = RedisConstant.WECHAT_TOKEN, key = "#p0", cacheManager = "days30CacheManager")
    public WxMpOAuth2AccessToken getToken(String code) {
        return null;
    }

    /**
     * 获取缓存
     * @param code
     * @return
     */
    @Cacheable(cacheNames = RedisConstant.WECHAT_USER, key = "#p0", cacheManager = "days30CacheManager")
    public WxMpUser getUser(String code) {
        return null;
    }

    /**
     * 删除缓存
     * @param code
     */
    @CacheEvict(cacheNames = RedisConstant.WECHAT_TOKEN, key = "#p0")
    public void deleteToken(String code) {

    }
}
