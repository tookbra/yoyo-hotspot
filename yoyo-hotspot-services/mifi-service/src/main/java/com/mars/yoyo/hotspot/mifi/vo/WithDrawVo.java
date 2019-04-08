package com.mars.yoyo.hotspot.mifi.vo;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author tookbra
 * @date 2018/6/22
 * @description
 */
@Data
@ToString
public class WithDrawVo implements Serializable {

    /**
     * 提现金额
     */
    private BigDecimal money;
    /**
     * 状态 0退款中 1成功 2失败
     */
    private int status;
    /**
     * 充值渠道
     */
    private String lang;
}
