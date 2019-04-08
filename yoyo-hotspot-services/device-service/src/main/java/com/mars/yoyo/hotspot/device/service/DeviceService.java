package com.mars.yoyo.hotspot.device.service;

import com.mars.yoyo.hotspot.device.domain.Device;

import java.util.List;

/**
 * @author tookbra
 * @date 2018/6/11
 * @description
 */
public interface DeviceService {

    /**
     * 查询设备
     * @param boxId 机柜id
     * @return
     */
    Device findByBoxId(String boxId);

    /**
     * 查询设备
     * @param token
     * @return
     */
    Device findByToken(String token);

    /**
     *
     * @param device
     * @return
     */
    Device save(Device device);

    /**
     *
     * @param device
     * @return
     */
    Device add(Device device);

    /**
     * 查询附近的设备
     * @param lng 经度
     * @param lat 纬度
     * @param radius 半径
     * @return
     */
    List<Device> findNearDeviceByPage(Double lng, Double lat, Integer radius);

    /**
     * 更新心跳时间
     * @param deviceId 设备id
     * @return
     */
    void updateHearTime(String deviceId);

    /**
     * 检查是否在线
     * @param token
     * @return
     */
    boolean checkState(String token);
}
