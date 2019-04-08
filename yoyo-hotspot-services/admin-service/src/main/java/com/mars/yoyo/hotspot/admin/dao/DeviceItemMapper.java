package com.mars.yoyo.hotspot.admin.dao;

import com.mars.yoyo.hotspot.admin.entity.DeviceItem;
import com.mars.yoyo.hotspot.mybatis.MyMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface DeviceItemMapper extends MyMapper<DeviceItem> {

    @Select("select count(1) from device_item where device_id = #{deviceId}")
    Integer itemTotalByDeviceId(@Param("deviceId") String deviceId);

}