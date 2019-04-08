package com.mars.yoyo.hotspot.mifi.web;

import com.mars.yoyo.hotspot.common.dps.annotation.Security;
import com.mars.yoyo.hotspot.common.dps.secutiry.UserEnv;
import com.mars.yoyo.hotspot.mifi.service.CouponService;
import com.mars.yoyo.hotspot.mifi.vo.CouponVo;
import com.mars.yoyo.hotspot.result.RestResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 优惠券模块
 * @author tookbra
 * @date 2018/5/22
 * @description
 */
@RestController
@RequestMapping("/coupon")
public class CouponController {


    @Autowired
    CouponService couponService;

    /**
     * 我的优惠券
     * @param userEnv
     * @return
     */
    @GetMapping
    @Security
    RestResult<List<CouponVo>> getCoupon(UserEnv userEnv) {
        List<CouponVo> list = couponService.getEnabledByUserId(userEnv.getUserId());
        return RestResult.success(list);
    }
}
