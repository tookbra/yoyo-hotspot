package com.mars.yoyo.hotspot.device.dao;

import com.mars.yoyo.hotspot.device.domain.Device;
import com.mars.yoyo.hotspot.mybatis.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DeviceMapper extends MyMapper<Device> {

    /**
     * 查询附近的设备
     * @param lng 经度
     * @param lat 纬度
     * @param radius 半径
     * @return
     */
    List<Device> findNearDeviceByPage(@Param("lng") Double lng, @Param("lat") Double lat, @Param("redius") Integer radius);
}