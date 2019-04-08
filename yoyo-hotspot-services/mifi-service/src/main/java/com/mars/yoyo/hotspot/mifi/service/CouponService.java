package com.mars.yoyo.hotspot.mifi.service;

import com.mars.yoyo.hotspot.mifi.domain.Coupon;
import com.mars.yoyo.hotspot.mifi.domain.UserCoupon;
import com.mars.yoyo.hotspot.mifi.pojo.UserCouponPojo;
import com.mars.yoyo.hotspot.mifi.vo.CouponVo;

import java.util.List;

/**
 * 优惠券
 * @author tookbra
 * @date 2018/5/17
 * @description
 */
public interface CouponService {

    /**
     * 获取优惠券
     * @param channelId 渠道id
     * @param type 获取条件
     * @return
     */
    @Deprecated
    Coupon getByChannel(int channelId, int type);

    /**
     * 获取优惠券
     * @param type 获取条件
     * @return
     */
    Coupon getByChannel(int type);

    /**
     * 发放优惠券
     * @param userCoupon {@link UserCoupon}
     */
    void addCoupon(UserCoupon userCoupon);

    /**
     * 更新用户优惠券
     * @param userCoupon
     */
    void saveUserCoupon(UserCoupon userCoupon);

    /**
     * 使用优惠券
     * @param userCouponId 优惠券id
     */
    void usedCoupon(int userCouponId);

    /**
     * 查询优惠券
     * @param userCouponId 优惠券id
     * @return {@link UserCoupon}
     */
    UserCoupon findByUserCouponId(int userCouponId);

    /**
     * 查询可用的优惠券
     * @param userId 用户id
     * @return {@link CouponVo}
     */
    List<CouponVo> getEnabledByUserId(int userId);

    /**
     * 查询可用优惠券
     * @param userId 用户id
     * @param userCouponId 用户优惠券id
     * @return {@link UserCouponPojo}
     */
    UserCouponPojo getByUserCouponId(int userId, int userCouponId);

    /**
     * 查询用户优惠券
     * @param userId 用户id
     * @param couponId 优惠券id
     * @return
     */
    List<UserCoupon> findByUserId(int userId, int couponId);

}
