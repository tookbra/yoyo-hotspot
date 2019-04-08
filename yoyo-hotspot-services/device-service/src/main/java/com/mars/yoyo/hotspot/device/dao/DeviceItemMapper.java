package com.mars.yoyo.hotspot.device.dao;

import com.mars.yoyo.hotspot.device.domain.DeviceItem;
import com.mars.yoyo.hotspot.mybatis.MyMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface DeviceItemMapper extends MyMapper<DeviceItem> {

    /**
     * 批量更新
     * @param list
     */
    void batchUpdate(@Param("list") List<DeviceItem> list);

    /**
     * 更新设备已被租借
     * @param deviceId
     */
    @Update("update device_item set leased = 1 where device_id = #{deviceId}")
    void updateLeasedByDeviceId(@Param("deviceId") String deviceId);
}