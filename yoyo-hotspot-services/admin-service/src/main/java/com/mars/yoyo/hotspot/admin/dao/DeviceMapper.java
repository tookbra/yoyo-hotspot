package com.mars.yoyo.hotspot.admin.dao;

import com.mars.yoyo.hotspot.admin.entity.Device;
import com.mars.yoyo.hotspot.mybatis.MyMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DeviceMapper extends MyMapper<Device> {
}