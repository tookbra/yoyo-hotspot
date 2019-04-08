package com.mars.yoyo.hotspot.admin.entity;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "user_account_withdraw")
public class UserAccountWithdraw {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private Integer userId;

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
    @Column(name = "order_no")
    private String orderNo;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 修改时间
     */
    @Column(name = "modify_time")
    private Date modifyTime;

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
     * 获取提现金额
     *
     * @return money - 提现金额
     */
    public BigDecimal getMoney() {
        return money;
    }

    /**
     * 设置提现金额
     *
     * @param money 提现金额
     */
    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    /**
     * 获取提现账户
     *
     * @return account - 提现账户
     */
    public String getAccount() {
        return account;
    }

    /**
     * 设置提现账户
     *
     * @param account 提现账户
     */
    public void setAccount(String account) {
        this.account = account;
    }

    /**
     * 获取状态 0申请中 1提现成功 2提现失败
     *
     * @return status - 状态 0申请中 1提现成功 2提现失败
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置状态 0申请中 1提现成功 2提现失败
     *
     * @param status 状态 0申请中 1提现成功 2提现失败
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取当时的订单号
     *
     * @return order_no - 当时的订单号
     */
    public String getOrderNo() {
        return orderNo;
    }

    /**
     * 设置当时的订单号
     *
     * @param orderNo 当时的订单号
     */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
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
     * 获取修改时间
     *
     * @return modify_time - 修改时间
     */
    public Date getModifyTime() {
        return modifyTime;
    }

    /**
     * 设置修改时间
     *
     * @param modifyTime 修改时间
     */
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}