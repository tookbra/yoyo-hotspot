package com.mars.yoyo.hotspot.device.service;

import com.mars.yoyo.hotspot.device.domain.DeliveryChannel;

/**
 * @author tookbra
 * @date 2018/6/15
 * @description
 */
public interface DeliveryChannelService {

    /**
     * 查询渠道
     * @param id
     * @return
     */
    DeliveryChannel findById(int id);
}
