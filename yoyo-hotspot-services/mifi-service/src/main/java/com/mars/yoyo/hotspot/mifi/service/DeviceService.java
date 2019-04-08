package com.mars.yoyo.hotspot.mifi.service;

import com.mars.yoyo.hotspot.mifi.domain.Device;
import com.mars.yoyo.hotspot.mifi.vo.BannerVo;
import com.mars.yoyo.hotspot.mifi.vo.DeviceVo;

/**
 * @author tookbra
 * @date 2018/5/22
 * @description
 */
public interface DeviceService {

    /**
     * 查询设备
     * @param token 设备id
     * @return
     */
    Device getByToken(String token);

    /**
     * 查询附近的设备
     * @param lng 经度
     * @param lat 纬度
     * @param radius 半径
     */
    void findNearDeviceByPage(Double lng, Double lat, Integer radius);

    /**
     * 查询设备
     * @param channelId
     * @return
     */
    DeviceVo findByChannelId(String channelId);

    /**
     *
     * @param channelId
     * @return
     */
    BannerVo getBanner(String channelId);
}
