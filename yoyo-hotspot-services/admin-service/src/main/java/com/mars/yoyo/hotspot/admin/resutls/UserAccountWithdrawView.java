package com.mars.yoyo.hotspot.admin.resutls;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class UserAccountWithdrawView implements Serializable {

    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 用户名称
     */
    private String name;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 国家(通过区号进行判断)
     */
    private String country;

    /**
     * 是否国际版本 0否 1是
     */
    private Boolean en;

    /**
     * 提现金额
     */
    private BigDecimal money;

    /**
     * 提现账户
     */
    private String account;

    /**
     * 状态 0申请中 1提现成功 2提现失败
     */
    private Integer status;

    /**
     * 当时的订单号
     */
    private String orderNo;

    /**
     * 订单状态
     */
    private Integer orderState;

    /**
     * 账户余额
     */
    private BigDecimal balance;

    /**
     * 账户余额，美元
     */
    private BigDecimal balanceEn;

    /**
     * 押金
     */
    private BigDecimal deposit;

    /**
     * 押金
     */
    private BigDecimal depositEn;

    /**
     * 创建时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

}