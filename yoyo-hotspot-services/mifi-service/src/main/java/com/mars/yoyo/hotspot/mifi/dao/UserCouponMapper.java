package com.mars.yoyo.hotspot.mifi.dao;

import com.mars.yoyo.hotspot.mifi.domain.UserCoupon;
import com.mars.yoyo.hotspot.mifi.pojo.UserCouponPojo;
import com.mars.yoyo.hotspot.mybatis.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserCouponMapper extends MyMapper<UserCoupon> {

    /**
     * 查询用户可用优惠券
     * @param userId 用户id
     * @return {@link UserCouponPojo}
     */
    List<UserCouponPojo> selectEnableByUserId(@Param("userId") int userId);

    /**
     * 查询用户可用优惠券
     * @param userId 用户id
     * @param userCouponId 用户优惠券id
     * @return {@link UserCouponPojo}
     */
    UserCouponPojo selectByUserCouponId(@Param("userId") int userId, @Param("userCouponId") int userCouponId);
}