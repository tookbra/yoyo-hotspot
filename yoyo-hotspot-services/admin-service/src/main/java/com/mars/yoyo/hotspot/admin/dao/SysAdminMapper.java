package com.mars.yoyo.hotspot.admin.dao;

import com.mars.yoyo.hotspot.admin.entity.SysAdmin;
import com.mars.yoyo.hotspot.mybatis.MyMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysAdminMapper extends MyMapper<SysAdmin> {
}