package com.mars.yoyo.hotspot.auth.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author tookbra
 * @date 2018/5/25
 * @description
 */
@Data
public class LoginAppVo {

    private String token;

    /**
     * 优惠券金额
     */
    private BigDecimal money;
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
    /**
     * 用户id
     */
    private Integer userId;
}
