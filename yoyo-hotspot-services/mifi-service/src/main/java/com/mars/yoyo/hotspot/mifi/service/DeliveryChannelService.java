package com.mars.yoyo.hotspot.mifi.service;

import com.mars.yoyo.hotspot.mifi.domain.DeliveryChannel;

import java.util.List;

/**
 * @author tookbra
 * @date 2018/6/3
 * @description
 */
public interface DeliveryChannelService {

    /**
     * 投放渠道
     * @return
     */
    List<DeliveryChannel> findAll();

    /**
     * 查询渠道
     * @param id
     * @return
     */
    DeliveryChannel findById(int id);
}
