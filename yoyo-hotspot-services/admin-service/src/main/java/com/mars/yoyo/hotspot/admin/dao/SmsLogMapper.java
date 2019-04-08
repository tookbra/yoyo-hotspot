package com.mars.yoyo.hotspot.admin.dao;

import com.mars.yoyo.hotspot.admin.entity.SmsLog;
import com.mars.yoyo.hotspot.mybatis.MyMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SmsLogMapper extends MyMapper<SmsLog> {
}