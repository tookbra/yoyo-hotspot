package com.mars.yoyo.hotspot.mifi.domain;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "user_account_recharge")
public class UserAccountRecharge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private Integer userId;

    private BigDecimal amount;

    @Column(name = "order_no")
    private String orderNo;

    /**
     * 状态 0 初始 1处理中 2成功 3失败
     */
    private Integer status;

    /**
     * 支付渠道 1 账户余额 2微信支付 3支付宝 4paypal
     */
    @Column(name = "pay_channel")
    private Integer payChannel;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "modity_time")
    private Date modityTime;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取用户id
     *
     * @return user_id - 用户id
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * 设置用户id
     *
     * @param userId 用户id
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * @return amount
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * @param amount
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * @return order_no
     */
    public String getOrderNo() {
        return orderNo;
    }

    /**
     * @param orderNo
     */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    /**
     * 获取状态 0 初始 1处理中 2成功 3失败
     *
     * @return status - 状态 0 初始 1处理中 2成功 3失败
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置状态 0 初始 1处理中 2成功 3失败
     *
     * @param status 状态 0 初始 1处理中 2成功 3失败
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取支付渠道 1 账户余额 2微信支付 3支付宝 4paypal
     *
     * @return pay_channel - 支付渠道 1 账户余额 2微信支付 3支付宝 4paypal
     */
    public Integer getPayChannel() {
        return payChannel;
    }

    /**
     * 设置支付渠道 1 账户余额 2微信支付 3支付宝 4paypal
     *
     * @param payChannel 支付渠道 1 账户余额 2微信支付 3支付宝 4paypal
     */
    public void setPayChannel(Integer payChannel) {
        this.payChannel = payChannel;
    }

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return modity_time
     */
    public Date getModityTime() {
        return modityTime;
    }

    /**
     * @param modityTime
     */
    public void setModityTime(Date modityTime) {
        this.modityTime = modityTime;
    }
}