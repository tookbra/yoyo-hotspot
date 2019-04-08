package com.mars.yoyo.hotspot.mifi.domain;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "user_account")
public class UserAccount {
    /**
     * 用户id
     */
    @Id
    @Column(name = "user_id")
    private Integer userId;

    /**
     * 账户余额
     */
    private BigDecimal balance;

    /**
     * 优惠券数量
     */
    @Column(name = "coupon_num")
    private Integer couponNum;

    /**
     * 红包数量
     */
    @Column(name = "red_envelope_num")
    private Integer redEnvelopeNum;

    /**
     * 积分
     */
    private Integer integral;

    /**
     * 押金
     */
    private BigDecimal deposit;

    /**
     * 押金
     */
    @Column(name = "deposit_en")
    private BigDecimal depositEn;

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
     * 账户余额，美元
     */
    @Column(name = "balance_en")
    private BigDecimal balanceEn;

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
     * 获取账户余额
     *
     * @return balance - 账户余额
     */
    public BigDecimal getBalance() {
        return balance;
    }

    /**
     * 设置账户余额
     *
     * @param balance 账户余额
     */
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    /**
     * 获取优惠券数量
     *
     * @return coupon_num - 优惠券数量
     */
    public Integer getCouponNum() {
        return couponNum;
    }

    /**
     * 设置优惠券数量
     *
     * @param couponNum 优惠券数量
     */
    public void setCouponNum(Integer couponNum) {
        this.couponNum = couponNum;
    }

    /**
     * 获取红包数量
     *
     * @return red_envelope_num - 红包数量
     */
    public Integer getRedEnvelopeNum() {
        return redEnvelopeNum;
    }

    /**
     * 设置红包数量
     *
     * @param redEnvelopeNum 红包数量
     */
    public void setRedEnvelopeNum(Integer redEnvelopeNum) {
        this.redEnvelopeNum = redEnvelopeNum;
    }

    /**
     * 获取积分
     *
     * @return integral - 积分
     */
    public Integer getIntegral() {
        return integral;
    }

    /**
     * 设置积分
     *
     * @param integral 积分
     */
    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    /**
     * 获取押金
     *
     * @return deposit - 押金
     */
    public BigDecimal getDeposit() {
        return deposit;
    }

    /**
     * 设置押金
     *
     * @param deposit 押金
     */
    public void setDeposit(BigDecimal deposit) {
        this.deposit = deposit;
    }

    /**
     * 获取押金
     *
     * @return deposit_en - 押金
     */
    public BigDecimal getDepositEn() {
        return depositEn;
    }

    /**
     * 设置押金
     *
     * @param depositEn 押金
     */
    public void setDepositEn(BigDecimal depositEn) {
        this.depositEn = depositEn;
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

    /**
     * 获取账户余额，美元
     *
     * @return balance_en - 账户余额，美元
     */
    public BigDecimal getBalanceEn() {
        return balanceEn;
    }

    /**
     * 设置账户余额，美元
     *
     * @param balanceEn 账户余额，美元
     */
    public void setBalanceEn(BigDecimal balanceEn) {
        this.balanceEn = balanceEn;
    }
}