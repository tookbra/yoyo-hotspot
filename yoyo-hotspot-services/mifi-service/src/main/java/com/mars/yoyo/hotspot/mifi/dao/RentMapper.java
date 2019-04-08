package com.mars.yoyo.hotspot.mifi.dao;

import com.mars.yoyo.hotspot.mifi.domain.Rent;
import com.mars.yoyo.hotspot.mybatis.MyMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RentMapper extends MyMapper<Rent> {

    /**
     * 查询租借记录
     * @param userId
     * @return
     */
    List<Rent> selectByUserId(@Param("userId") int userId);

    /**
     *
     * @param userId
     * @return
     */
    @Select("select count(1) from rent where user_id = #{userId} and lang_en = 1")
    int countByEn(@Param("userId") int userId);
}