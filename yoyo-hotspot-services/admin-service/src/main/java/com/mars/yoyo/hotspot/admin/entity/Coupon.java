package com.mars.yoyo.hotspot.admin.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 优惠券名称
     */
    private String name;

    /**
     * 优惠券使用类型 0无门槛 1满减
     */
    @Column(name = "use_type")
    private Integer useType;

    /**
     * 获取渠道 1 酒店
     */
    private Integer channel;

    /**
     * 获取条件 1绑定手机
     */
    private Integer conditions;

    /**
     * 优惠券金额
     */
    private BigDecimal money;

    /**
     * 0-无效；1-有效； 2-过期；
     */
    private Integer status;

    /**
     * 是否删除：0-未删除，1-已删除
     */
    private Integer deleted;

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
    @Column(name = "begin_date")
    private Date beginDate;

    /**
     * 结束日期
     */
    @Column(name = "end_date")
    private Date endDate;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "modify_time")
    private Date modifyTime;

    /**
     * 是否是国际版优惠券
     */
    @Column(name = "lang_en")
    private Integer langEn;

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
     * 获取优惠券名称
     *
     * @return name - 优惠券名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置优惠券名称
     *
     * @param name 优惠券名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取优惠券使用类型 0无门槛 1满减
     *
     * @return use_type - 优惠券使用类型 0无门槛 1满减
     */
    public Integer getUseType() {
        return useType;
    }

    /**
     * 设置优惠券使用类型 0无门槛 1满减
     *
     * @param useType 优惠券使用类型 0无门槛 1满减
     */
    public void setUseType(Integer useType) {
        this.useType = useType;
    }

    /**
     * 获取获取渠道
1 酒店
     *
     * @return channel - 获取渠道
1 酒店
     */
    public Integer getChannel() {
        return channel;
    }

    /**
     * 设置获取渠道
1 酒店
     *
     * @param channel 获取渠道
1 酒店
     */
    public void setChannel(Integer channel) {
        this.channel = channel;
    }

    /**
     * 获取获取条件 1绑定手机
     *
     * @return conditions - 获取条件 1绑定手机
     */
    public Integer getConditions() {
        return conditions;
    }

    /**
     * 设置获取条件 1绑定手机
     *
     * @param conditions 获取条件 1绑定手机
     */
    public void setConditions(Integer conditions) {
        this.conditions = conditions;
    }

    /**
     * 获取优惠券金额
     *
     * @return money - 优惠券金额
     */
    public BigDecimal getMoney() {
        return money;
    }

    /**
     * 设置优惠券金额
     *
     * @param money 优惠券金额
     */
    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    /**
     * 获取0-无效；1-有效； 2-过期；
     *
     * @return status - 0-无效；1-有效； 2-过期；
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置0-无效；1-有效； 2-过期；
     *
     * @param status 0-无效；1-有效； 2-过期；
     */
    public void setStatus(Integer status) {
        this.status = status;
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
     * 获取数量
     *
     * @return num - 数量
     */
    public Integer getNum() {
        return num;
    }

    /**
     * 设置数量
     *
     * @param num 数量
     */
    public void setNum(Integer num) {
        this.num = num;
    }

    /**
     * 获取描述
     *
     * @return description - 描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置描述
     *
     * @param description 描述
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 获取开始日期
     *
     * @return begin_date - 开始日期
     */
    public Date getBeginDate() {
        return beginDate;
    }

    /**
     * 设置开始日期
     *
     * @param beginDate 开始日期
     */
    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    /**
     * 获取结束日期
     *
     * @return end_date - 结束日期
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * 设置结束日期
     *
     * @param endDate 结束日期
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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
     * @return modify_time - 更新时间
     */
    public Date getModifyTime() {
        return modifyTime;
    }

    /**
     * 设置更新时间
     *
     * @param modifyTime 更新时间
     */
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * 获取是否是国际版优惠券
     *
     * @return lang_en - 是否是国际版优惠券
     */
    public Integer getLangEn() {
        return langEn;
    }

    /**
     * 设置是否是国际版优惠券
     *
     * @param langEn 是否是国际版优惠券
     */
    public void setLangEn(Integer langEn) {
        this.langEn = langEn;
    }
}