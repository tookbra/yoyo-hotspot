package com.mars.yoyo.hotspot.mifi.vo;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author tookbra
 * @date 2018/5/22
 * @description
 */
@Data
@ToString
public class CouponVo {

    private Integer id;

    /**
     * 金额
     */
    private BigDecimal money;
    /**
     * 过期时间
     */
    private Date endTime;
    /**
     * 是否过期
     */
    private boolean expired;
}
