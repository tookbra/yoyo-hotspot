package com.mars.yoyo.hotspot.admin.service;

import com.mars.yoyo.hotspot.admin.params.CommonParameter;
import com.mars.yoyo.hotspot.admin.params.CouponParameter;
import com.mars.yoyo.hotspot.admin.resutls.CouponView;
import com.mars.yoyo.hotspot.admin.resutls.base.ListResultEx;
import com.mars.yoyo.hotspot.admin.resutls.base.ResultEx;
import com.mars.yoyo.hotspot.result.RestResult;

/**
 * 优惠券接口
 *
 * @author admin
 * @create 2018/5/14
 * @since 1.0.0
 */
public interface CouponService {

    /**
     * 分页查询优惠券
     * @return
     */
    ListResultEx<CouponView> listCoupons(CommonParameter parameter);

    /**
     * 添加优惠券
     * @param parameter
     * @return
     */
    RestResult addCouponInfo(CouponParameter parameter);

    /**
     * 删除优惠券(为逻辑删除)
     * @param couponId
     * @return
     */
    RestResult deleteCoupon(Integer couponId);

    /**
     * 更新优惠券信息
     * @param parameter
     * @return
     */
    RestResult updateCoupon(CouponParameter parameter);

    /**
     * 查询优惠券的详细信息
     * @param couponId
     * @return
     */
    RestResult getCouponById(Integer couponId);

}
