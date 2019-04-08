package com.mars.yoyo.hotspot.mifi.domain;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "red_envelope_item")
public class RedEnvelopeItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 红包id
     */
    @Column(name = "red_envelope_id")
    private Integer redEnvelopeId;

    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 是否被领取 1是0否
     */
    @Column(name = "is_receive")
    private Boolean isReceive;

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
     * 获取金额
     *
     * @return amount - 金额
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * 设置金额
     *
     * @param amount 金额
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * 获取是否被领取 1是0否
     *
     * @return is_receive - 是否被领取 1是0否
     */
    public Boolean getIsReceive() {
        return isReceive;
    }

    /**
     * 设置是否被领取 1是0否
     *
     * @param isReceive 是否被领取 1是0否
     */
    public void setIsReceive(Boolean isReceive) {
        this.isReceive = isReceive;
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