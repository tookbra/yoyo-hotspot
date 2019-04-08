package com.mars.yoyo.hotspot.admin.resutls;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户优惠券
 *
 * @author admin
 * @create 2018/5/16
 */
@Data
public class UserCouponView implements Serializable {

    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 会员名称
     */
    private String userName;

    /**
     * 优惠券id
     */
    private Integer couponId;

    /**
     * 优惠券名称
     */
    private String name;

    /**
     * 优惠券金额
     */
    private BigDecimal money;

    /**
     * 是否有效 0-无效；1-有效； 2-过期；
     */
    private Integer status;

    /**
     * 是否使用 0 否 1 是
     */
    private Integer state;

    /**
     * 使用时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date useTime;

    /**
     * 优惠券使用类型 0无门槛 1满减
     */
    private Integer useType;

    /**
     * 获取渠道 1 酒店
     */
    private Integer channel;

    private String channelName;


    /**
     * 获取条件 1绑定手机
     */
    private Integer conditions;

    private String conditionsName;

    /**
     * 描述
     */
    private String description;

    /**
     * 开始日期
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date beginDate;

    /**
     * 结束日期
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date endDate;

}
