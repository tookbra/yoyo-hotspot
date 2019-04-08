package com.mars.yoyo.hotspot.mifi.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * @author tookbra
 * @date 2018/5/15
 * @description
 */
@Data
@ToString
public class UserVo {

    /**
     * 账户余额
     */
    private BigDecimal balance;
    /**
     * 优惠券数量
     */
    private int couponNum;
    /**
     * 红包数量
     */
    private int redEnvelopeNum;
    /**
     * 是否交纳押金
     */
    private boolean deposited;
    /**
     * 是否需要交押金
     */
    private boolean needDeposited;
    /**
     * 是否认证
     */
    private boolean certified;
    /**
     * 押金金额
     */
    private BigDecimal deposit;
    /**
     * 用户积分
     */
    private int integral;
    /**
     * 租借信息
     */
    @JsonProperty("rentInfo")
    private LeaseStateVo leaseStateVo;
    /**
     * 是否国际版租借过
     */
    private boolean international;
    /**
     * 是否国际版
     */
    private boolean en;

}
