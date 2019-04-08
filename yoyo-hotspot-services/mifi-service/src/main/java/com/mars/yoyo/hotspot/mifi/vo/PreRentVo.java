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
public class PreRentVo {

    /**
     * 套餐名称
     */
    private String productName;
    /**
     * 套餐类型
     */
    private int productType;
    /**
     * 价格
     */
    private String priceMsg;
    /**
     * 价格
     */
    private BigDecimal price;
    /**
     * 使用时长
     */
    private int usageHour;
    private long usageTime;
    /**
     * 应付金额
     */
    private BigDecimal amountPayable;
    /**
     * 预估金额
     */
    private BigDecimal amount;
    /**
     * 租借时间
     */
    private Date rentTime;
    /**
     * 预计归还时间
     */
    private Date returnTime;
    /**
     * 租借地点
     */
    private String location;
    /**
     * 租借编号
     */
    private String rentNo;
    /**
     * 设备类型
     */
    private String deviceType = "随身WIFI";

    private String wifi = "mefi";

    private String pwd;

    private BigDecimal payAmount;

    private CurrentRent currentRent;

    @Data
    public static class CurrentRent {
        /**
         * 套餐名称
         */
        private String productName;
        /**
         * 价格
         */
        private String priceMsg;
        /**
         * 价格
         */
        private BigDecimal price;
        /**
         * 合计
         */
        private BigDecimal priceTotal;
        /**
         * 类型
         */
        private Integer type;
        /**
         * 订单号
         */
        private String orderNo;

        private String currentNo;

        private int rentId;

        private Integer productId;

        private boolean returned;

        private boolean payed = true;

        private boolean isEn;

        private Date rentTime;

        private boolean show = true;

        private boolean maturity;
    }

}
