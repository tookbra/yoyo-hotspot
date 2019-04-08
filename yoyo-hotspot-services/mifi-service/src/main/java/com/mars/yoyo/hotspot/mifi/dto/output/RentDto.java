package com.mars.yoyo.hotspot.mifi.dto.output;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author tookbra
 * @date 2018/6/5
 * @description
 */
@Data
public class RentDto {

    /**
     * 套餐类型
     */
    private String productName;
    /**
     * 类型
     */
    private int type;
    /**
     * 使用时长
     */
    private int usedTime;
    /**
     * 租借地址
     */
    private String location;
    /**
     * 租借时间
     */
    private Date rentTime;
    /**
     * 租借编号
     */
    private String rentNo;
    /**
     * 设备类型
     */
    private String deviceType;
    /**
     * 是否归还
     */
    private boolean returned;
    /**
     * 价格
     */
    private BigDecimal price;
    /**
     * 是否到期
     */
    private boolean maturity;
    /**
     * 是否国际版
     */
    private boolean international;
    /**
     * 租借id
     */
    private int rentId;
    /**
     * 总计
     */
    private BigDecimal priceTotal;

    private boolean payed;

    private int expetcedState;

}
