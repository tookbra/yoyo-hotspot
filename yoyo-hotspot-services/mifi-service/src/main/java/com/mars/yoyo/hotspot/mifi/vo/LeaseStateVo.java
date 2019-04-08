package com.mars.yoyo.hotspot.mifi.vo;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author tookbra
 * @date 2018/7/12
 * @description
 */
@Data
@ToString
public class LeaseStateVo implements Serializable {

    /**
     * 是否完成支付
     */
    private boolean payed;
    /**
     * 产品类型
     */
    private int productType;
    /**
     * 租借时间
     */
    private int time;
    /**
     * 产品名称
     */
    private String productName;
    /**
     * 是否归还
     */
    private boolean returned;
    /**
     * 是否预付费
     */
    private boolean prepaid;
    private BigDecimal amount;
}
