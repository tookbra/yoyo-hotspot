package com.mars.yoyo.hotspot.mifi.service;

import com.mars.yoyo.hotspot.mifi.BaseTest;
import com.mars.yoyo.hotspot.mifi.pojo.UserCouponPojo;
import com.mars.yoyo.hotspot.mifi.vo.CouponVo;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author tookbra
 * @date 2018/5/23
 * @description
 */
public class CouponServiceTest extends BaseTest {

    @Autowired
    CouponService couponService;

    @Test
    public void getEnabledByUserIdTest() {
        List<CouponVo> list = couponService.getEnabledByUserId(1);
        System.out.println(list);
    }

    @Test
    public void getByUserCouponIdTest() {
        UserCouponPojo userCouponPojo =  couponService.getByUserCouponId(1, 1);
        System.out.println(userCouponPojo);
        Assert.assertNotNull(userCouponPojo);
    }
}
