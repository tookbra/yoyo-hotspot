package com.mars.yoyo.hotspot.mifi.domain;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private Integer userId;

    /**
     * 订单编号
     */
    @Column(name = "order_no")
    private String orderNo;

    /**
     * 状态 1处理中 2成功 3失败
     */
    private Integer state;

    /**
     * 金额
     */
    private BigDecimal money;

    /**
     * 支付渠道 1 账户余额 2微信支付 3支付宝
     */
    @Column(name = "pay_channel")
    private Integer payChannel;

    /**
     * 订单类型 1押金 2充值 3租借
     */
    @Column(name = "order_type")
    private Integer orderType;

    /**
     * 备注
     */
    private String description;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "mofidy_time")
    private Date mofidyTime;

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
     * 获取订单编号
     *
     * @return order_no - 订单编号
     */
    public String getOrderNo() {
        return orderNo;
    }

    /**
     * 设置订单编号
     *
     * @param orderNo 订单编号
     */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    /**
     * 获取状态 1处理中 2成功 3失败
     *
     * @return state - 状态 1处理中 2成功 3失败
     */
    public Integer getState() {
        return state;
    }

    /**
     * 设置状态 1处理中 2成功 3失败
     *
     * @param state 状态 1处理中 2成功 3失败
     */
    public void setState(Integer state) {
        this.state = state;
    }

    /**
     * 获取金额
     *
     * @return money - 金额
     */
    public BigDecimal getMoney() {
        return money;
    }

    /**
     * 设置金额
     *
     * @param money 金额
     */
    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    /**
     * 获取支付渠道 1 账户余额 2微信支付 3支付宝
     *
     * @return pay_channel - 支付渠道 1 账户余额 2微信支付 3支付宝
     */
    public Integer getPayChannel() {
        return payChannel;
    }

    /**
     * 设置支付渠道 1 账户余额 2微信支付 3支付宝
     *
     * @param payChannel 支付渠道 1 账户余额 2微信支付 3支付宝
     */
    public void setPayChannel(Integer payChannel) {
        this.payChannel = payChannel;
    }

    /**
     * 获取订单类型 1押金 2充值 3租借
     *
     * @return order_type - 订单类型 1押金 2充值 3租借
     */
    public Integer getOrderType() {
        return orderType;
    }

    /**
     * 设置订单类型 1押金 2充值 3租借
     *
     * @param orderType 订单类型 1押金 2充值 3租借
     */
    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    /**
     * 获取备注
     *
     * @return description - 备注
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置备注
     *
     * @param description 备注
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取更新时间
     *
     * @return mofidy_time - 更新时间
     */
    public Date getMofidyTime() {
        return mofidyTime;
    }

    /**
     * 设置更新时间
     *
     * @param mofidyTime 更新时间
     */
    public void setMofidyTime(Date mofidyTime) {
        this.mofidyTime = mofidyTime;
    }
}