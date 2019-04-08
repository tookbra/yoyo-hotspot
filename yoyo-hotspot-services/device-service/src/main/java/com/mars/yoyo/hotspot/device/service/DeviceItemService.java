package com.mars.yoyo.hotspot.device.service;

import com.mars.yoyo.hotspot.device.domain.DeviceItem;

import java.util.List;

/**
 * @author tookbra
 * @date 2018/6/12
 * @description
 */
public interface DeviceItemService {

    /**
     * 查询wifi设备
     * @param boxId
     * @return
     */
    List<DeviceItem> findByBoxId(String boxId);

    /**
     * 保存wifi设备
     * @param list
     * @return
     */
    List<DeviceItem> add(List<DeviceItem> list);

    /**
     * 插入设备
     * @param item
     */
    void add(DeviceItem item);

    /**
     * 更新wifi设备
     * @param list
     */
    void save(List<DeviceItem> list);

    /**
     * 更新wifi设备
     * @param deviceItem
     */
    void save(DeviceItem deviceItem);

    /**
     * 更新设备已被租借
     * @param deviceId 设备id
     */
    void updateLeasedByDeviceId(String deviceId);

    /**
     * 查询wifi设备
     * @param boxId 设备id
     * @return
     */
    DeviceItem findTop1ByBoxId(String boxId);

    /**
     * 查询wifi设备
     * @param powerId 充电宝id
     * @return
     */
    DeviceItem findByPowerId(String deviceId, String powerId);
}
