package com.mars.yoyo.hotspot.mybatis;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

/**
 * @author tookbra
 * @date 2018/4/24
 * @description
 */
public interface MyMapper<T> extends Mapper<T>, MySqlMapper<T>, InsertListMapper<T> {
}
