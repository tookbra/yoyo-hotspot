package com.mars.yoyo.hotspot.admin.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "red_envelope")
public class RedEnvelope {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 金额
     */
    private BigDecimal money;

    /**
     * 是否可用 1是 0 否
     */
    private Integer state;

    /**
     * 是否删除：0-未删除，1-已删除
     */
    private Integer deleted;

    /**
     * 红包类型 1支付押金
     */
    private Integer channel;

    /**
     * 开始时间
     */
    @Column(name = "begin_time")
    private Date beginTime;

    /**
     * 过期时间
     */
    @Column(name = "end_time")
    private Date endTime;

    /**
     * 红包数量
     */
    private Integer count;

    /**
     * 是否已生成 1是 0否
     */
    @Column(name = "is_generate")
    private Integer isGenerate;

    /**
     * 是否国际版 1是 0 否
     */
    @Column(name = "is_en")
    private Integer isEn;

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
     * 获取是否可用 1是 0 否
     *
     * @return state - 是否可用 1是 0 否
     */
    public Integer getState() {
        return state;
    }

    /**
     * 设置是否可用 1是 0 否
     *
     * @param state 是否可用 1是 0 否
     */
    public void setState(Integer state) {
        this.state = state;
    }

    /**
     * 获取是否删除：0-未删除，1-已删除
     *
     * @return deleted - 是否删除：0-未删除，1-已删除
     */
    public Integer getDeleted() {
        return deleted;
    }

    /**
     * 设置是否删除：0-未删除，1-已删除
     *
     * @param deleted 是否删除：0-未删除，1-已删除
     */
    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    /**
     * 获取红包类型 1支付押金
     *
     * @return channel - 红包类型 1支付押金
     */
    public Integer getChannel() {
        return channel;
    }

    /**
     * 设置红包类型 1支付押金
     *
     * @param channel 红包类型 1支付押金
     */
    public void setChannel(Integer channel) {
        this.channel = channel;
    }

    /**
     * 获取开始时间
     *
     * @return begin_time - 开始时间
     */
    public Date getBeginTime() {
        return beginTime;
    }

    /**
     * 设置开始时间
     *
     * @param beginTime 开始时间
     */
    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    /**
     * 获取过期时间
     *
     * @return end_time - 过期时间
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * 设置过期时间
     *
     * @param endTime 过期时间
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * 获取红包数量
     *
     * @return count - 红包数量
     */
    public Integer getCount() {
        return count;
    }

    /**
     * 设置红包数量
     *
     * @param count 红包数量
     */
    public void setCount(Integer count) {
        this.count = count;
    }

    /**
     * 获取是否已生成 1是 0否
     *
     * @return is_generate - 是否已生成 1是 0否
     */
    public Integer getIsGenerate() {
        return isGenerate;
    }

    /**
     * 设置是否已生成 1是 0否
     *
     * @param isGenerate 是否已生成 1是 0否
     */
    public void setIsGenerate(Integer isGenerate) {
        this.isGenerate = isGenerate;
    }

    /**
     * 获取是否国际版 1是 0 否
     *
     * @return is_en - 是否国际版 1是 0 否
     */
    public Integer getIsEn() {
        return isEn;
    }

    /**
     * 设置是否国际版 1是 0 否
     *
     * @param isEn 是否国际版 1是 0 否
     */
    public void setIsEn(Integer isEn) {
        this.isEn = isEn;
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