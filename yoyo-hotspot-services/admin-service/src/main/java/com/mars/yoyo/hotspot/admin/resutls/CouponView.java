package com.mars.yoyo.hotspot.admin.resutls;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 优惠券结果参数
 *
 * @author admin
 * @create 2018/5/16
 */
@Data
public class CouponView implements Serializable {

    private static final long serialVersionUID = -6556754845337861403L;

    private Integer id;

    /**
     * 优惠券名称
     */
    private String name;

    /**
     * 优惠券使用类型 0无门槛 1满减
     */
    private Integer useType;

    /**
     * 获取渠道 1 酒店
     */
    private Integer channel;

    /**
     * 渠道名称
     */
    private String channelName;

    /**
     * 获取条件 1绑定手机
     */
    private Integer conditions;

    /**
     * 获取条件名称
     */
    private String conditionsName;

    /**
     * 优惠券金额
     */
    private BigDecimal money;

    /**
     * 0-无效；1-有效； 2-过期；
     */
    private Integer status;

    /**
     * 数量
     */
    private Integer num;

    /**
     * 描述
     */
    private String description;

    /**
     * 开始日期
     */
    @JSONField(format = "yyyy-MM-dd")
    private Date beginDate;

    /**
     * 结束日期
     */
    @JSONField(format = "yyyy-MM-dd")
    private Date endDate;

    /**
     * 创建时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 更新时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date modifyTime;

    /**
     * 是否是国际版优惠券
     */
    private Integer langEn;

}
