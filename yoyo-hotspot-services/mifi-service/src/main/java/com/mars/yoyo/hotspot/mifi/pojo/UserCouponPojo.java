package com.mars.yoyo.hotspot.mifi.pojo;

import com.mars.yoyo.hotspot.mifi.domain.Coupon;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @author tookbra
 * @date 2018/5/23
 * @description
 */
@ToString
public class UserCouponPojo extends Coupon {

    /**
     * 用户优惠券id
     */
    private int couponId;
    /**
     * 是否已用
     */
    private boolean state;
    /**
     * 使用时间
     */
    private Date useTime;

    public int getCouponId() {
        return couponId;
    }

    public void setCouponId(int couponId) {
        this.couponId = couponId;
    }

    public boolean getState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public Date getUseTime() {
        return useTime;
    }

    public void setUseTime(Date useTime) {
        this.useTime = useTime;
    }
}
