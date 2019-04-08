package com.mars.yoyo.hotspot.admin.params;

import lombok.Data;

/**
 * 优惠券请求参数
 *
 * @author admin
 * @create 2018/5/16
 */
@Data
public class CouponParameter extends SessionParameter {

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
     * 获取渠道1 酒店
     */
    private Integer channel;

    /**
     * 获取条件 1 绑定手机
     */
    private Integer conditions;

    /**
     * 优惠券金额
     */
    private String money;

    /**
     * 1 有效 2 过期
     */
    private Integer status;

    /**
     * 数量
     */
    private Integer num = Integer.valueOf(0);

    /**
     * 开始日期
     */
    private String beginDate;

    /**
     * 结束日期
     */
    private String endDate;

    /**
     * 描述
     */
    private String description;

    /**
     * 是否是国际版优惠券
     */
    private Integer langEn;

}
