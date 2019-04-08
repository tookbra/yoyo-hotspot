package com.mars.yoyo.hotspot.mifi.service;

import com.mars.yoyo.hotspot.mifi.domain.DeviceItem;

import java.util.List;

/**
 * @author tookbra
 * @date 2018/6/21
 * @description
 */
public interface DeviceItemService {

    /**
     * 查询充电宝
     * @param powerBankId 充电宝id
     * @return
     */
    DeviceItem findByPowerBankId(String deviceId, String powerBankId);

    DeviceItem findByPowerBankIdAndSlot(String powerBankId, int slot);

    DeviceItem save(DeviceItem deviceItem);

    /**
     * 查询充电宝设备
     * @param deviceId 机柜id
     * @return
     */
    List<DeviceItem> findByDeviceId(String deviceId);
}
