package com.mars.yoyo.hotspot.mifi.vo;

import lombok.Data;
import lombok.ToString;

/**
 * @author tookbra
 * @date 2018/6/9
 * @description
 */
@Data
@ToString
public class RentInfoVo {
    /**
     * 是否归还
     */
    private boolean returned;
    /**
     * 计时
     */
    private int time;
    /**
     * 租借模式
     */
    private int type;
    /**
     * 是否支付
     */
    private boolean payed;
    /**
     * 是否到期
     */
    private boolean maturity = false;
}
