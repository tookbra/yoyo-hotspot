package com.mars.yoyo.hotspot.mifi.cache;

import com.mars.yoyo.hotspot.mifi.constant.RedisConstant;
import com.mars.yoyo.hotspot.mifi.domain.DeliveryChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

/**
 * @author tookbra
 * @date 2018/6/3
 * @description
 */
@Slf4j
@Component
public class DeliveryChannelCache {

    /**
     * @param deliveryChannel
     * @return
     */
    @CachePut(cacheNames = RedisConstant.COMMON, key = "T(com.mars.yoyo.hotspot.mifi.constant.RedisConstant).DELIVERY_CHANNEL + #p0.token")
    public DeliveryChannel put(DeliveryChannel deliveryChannel) {
        return deliveryChannel;
    }

    /**
     *
     * @param channel 渠道
     * @return
     */
    @Cacheable(cacheNames = RedisConstant.COMMON, key = "T(com.mars.yoyo.hotspot.mifi.constant.RedisConstant).DELIVERY_CHANNEL + #p0")
    public DeliveryChannel get(String channel) {
        return null;
    }
}
