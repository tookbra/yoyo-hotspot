package com.mars.yoyo.hotspot.admin.entity;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "user_red_envelope")
public class UserRedEnvelope {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private Integer userId;

    /**
     * 红包id
     */
    @Column(name = "red_envelope_id")
    private Integer redEnvelopeId;

    /**
     * 红包id
     */
    @Column(name = "red_item_id")
    private Integer redItemId;

    /**
     * 红包金额
     */
    private BigDecimal amount;

    @Column(name = "create_time")
    private Date createTime;

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
     * 获取红包id
     *
     * @return red_envelope_id - 红包id
     */
    public Integer getRedEnvelopeId() {
        return redEnvelopeId;
    }

    /**
     * 设置红包id
     *
     * @param redEnvelopeId 红包id
     */
    public void setRedEnvelopeId(Integer redEnvelopeId) {
        this.redEnvelopeId = redEnvelopeId;
    }

    /**
     * 获取红包id
     *
     * @return red_item_id - 红包id
     */
    public Integer getRedItemId() {
        return redItemId;
    }

    /**
     * 设置红包id
     *
     * @param redItemId 红包id
     */
    public void setRedItemId(Integer redItemId) {
        this.redItemId = redItemId;
    }

    /**
     * 获取红包金额
     *
     * @return amount - 红包金额
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * 设置红包金额
     *
     * @param amount 红包金额
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
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
     * @return modify_time
     */
    public Date getModifyTime() {
        return modifyTime;
    }

    /**
     * @param modifyTime
     */
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}