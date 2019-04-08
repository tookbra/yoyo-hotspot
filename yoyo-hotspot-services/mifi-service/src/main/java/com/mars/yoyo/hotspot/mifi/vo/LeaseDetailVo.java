package com.mars.yoyo.hotspot.mifi.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author tookbra
 * @date 2018/7/12
 * @description
 */
@Data
@ToString
public class LeaseDetailVo implements Serializable {

    /**
     * wifi名称
     */
    private String wifi = "mefi";
    /**
     * wifi密码
     */
    private String pwd;
    /**
     * 产品名称
     */
    private String productName;
    /**
     * 应付金额
     */
    private BigDecimal amount;
    /**
     * 单价
     */
    private BigDecimal price;
    /**
     * 租赁时间
     */
    private Date rentTime;
    /**
     * 预计结束时间
     */
    private Date expectedReturnTime;
    /**
     * 租赁地点
     */
    private String location;
    /**
     * 订单编号
     */
    private String rentNo;
    /**
     * 设备类型
     */
    private String deviceType;
    /**
     * 使用时长（秒）
     */
    private long usedSec;
    /**
     * 使用时长（分）
     */
    private int usedMin;
    /**
     * 产品类型
     */
    private int productType;
    /**
     * 是否支付
     */
    private boolean payed;
    /**
     * 是否归还
     */
    private boolean returned;
    /**
     * 订单号
     */
    private String orderNo;
    /**
     * 是否过期
     */
    private boolean expired;
    /**
     * 租赁id
     */
    private int rentId;
    /**
     * 产品id
     */
    @JsonIgnore
    private Integer productId;

    /**
     * 是否弹出设备
     */
    private boolean state;

    private String device;

    private String deviceItem;

    /**
     * 是否显示
     */
    private boolean show;
}
