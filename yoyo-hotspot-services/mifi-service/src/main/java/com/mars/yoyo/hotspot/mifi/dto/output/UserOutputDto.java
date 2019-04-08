package com.mars.yoyo.hotspot.mifi.dto.output;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author tookbra
 * @date 2018/5/15
 * @description
 */
@Data
public class UserOutputDto{

    /**
     * 用户id
     */
    public Integer id;
    /**
     * 用户名
     */
    public String username;
    /**
     * 优惠券金额
     */
    public BigDecimal couponMoney;
    /**
     * 是否有优惠券
     */
    private boolean couponed = false;
    /**
     * 是否缴纳押金
     */
    private boolean deposit = false;
    /**
     * 是否国际版
     */
    private boolean en;
    /**
     * 优惠券地址
     */
    private String couponUrl;
}
