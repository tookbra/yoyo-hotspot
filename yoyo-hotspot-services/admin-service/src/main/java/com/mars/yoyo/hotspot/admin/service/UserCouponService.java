package com.mars.yoyo.hotspot.admin.service;

import com.mars.yoyo.hotspot.admin.params.PageParameter;
import com.mars.yoyo.hotspot.admin.resutls.UserCouponView;
import com.mars.yoyo.hotspot.admin.resutls.base.ListResultEx;

/**
 * 用户优惠券
 *
 * @author admin
 * @create 2018/5/16
 */
public interface UserCouponService {

    /**
     * 查询用户的优惠券
     * @param userId
     * @return
     */
    ListResultEx<UserCouponView> getUserCouponList(Integer userId, PageParameter parameter);

}
