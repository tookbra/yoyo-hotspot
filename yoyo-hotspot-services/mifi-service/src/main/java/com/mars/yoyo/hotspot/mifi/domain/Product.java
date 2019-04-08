package com.mars.yoyo.hotspot.mifi.domain;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 产品名称
     */
    @Column(name = "product_name")
    private String productName;

    /**
     * 产品类型
1小时借
2包天借
3包月借
     */
    @Column(name = "product_type")
    private Integer productType;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 描述
     */
    private String description;

    /**
     * 是否启用 0否 1是
     */
    private Boolean status;

    /**
     * 是否封顶 0否 1是
     */
    private Boolean caped;

    /**
     *  封顶价格
     */
    @Column(name = "cap_price")
    private BigDecimal capPrice;

    /**
     * 是否无限流量 0 否 1是
     */
    private Boolean unlimited;

    /**
     * 最大流量
     */
    private Integer limited;

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

    @Column(name = "product_name_en")
    private String productNameEn;

    @Column(name = "price_en")
    private BigDecimal priceEn;

    @Column(name = "description_en")
    private String descriptionEn;

    /**
     * 是否送流量
     */
    private Boolean gifted;

    /**
     * 流量大小
     */
    @Column(name = "limit_num")
    private Integer limitNum;

    @Column(name = "gift_msg")
    private String giftMsg;

    @Column(name = "gift_msg_en")
    private String giftMsgEn;

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
     * 获取产品名称
     *
     * @return product_name - 产品名称
     */
    public String getProductName() {
        return productName;
    }

    /**
     * 设置产品名称
     *
     * @param productName 产品名称
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     * 获取产品类型
1小时借
2包天借
3包月借
     *
     * @return product_type - 产品类型
1小时借
2包天借
3包月借
     */
    public Integer getProductType() {
        return productType;
    }

    /**
     * 设置产品类型
1小时借
2包天借
3包月借
     *
     * @param productType 产品类型
1小时借
2包天借
3包月借
     */
    public void setProductType(Integer productType) {
        this.productType = productType;
    }

    /**
     * 获取价格
     *
     * @return price - 价格
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * 设置价格
     *
     * @param price 价格
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
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
     * 获取是否启用 0否 1是
     *
     * @return status - 是否启用 0否 1是
     */
    public Boolean getStatus() {
        return status;
    }

    /**
     * 设置是否启用 0否 1是
     *
     * @param status 是否启用 0否 1是
     */
    public void setStatus(Boolean status) {
        this.status = status;
    }

    /**
     * 获取是否封顶 0否 1是
     *
     * @return caped - 是否封顶 0否 1是
     */
    public Boolean getCaped() {
        return caped;
    }

    /**
     * 设置是否封顶 0否 1是
     *
     * @param caped 是否封顶 0否 1是
     */
    public void setCaped(Boolean caped) {
        this.caped = caped;
    }

    /**
     * 获取 封顶价格
     *
     * @return cap_price -  封顶价格
     */
    public BigDecimal getCapPrice() {
        return capPrice;
    }

    /**
     * 设置 封顶价格
     *
     * @param capPrice  封顶价格
     */
    public void setCapPrice(BigDecimal capPrice) {
        this.capPrice = capPrice;
    }

    /**
     * 获取是否无限流量 0 否 1是
     *
     * @return unlimited - 是否无限流量 0 否 1是
     */
    public Boolean getUnlimited() {
        return unlimited;
    }

    /**
     * 设置是否无限流量 0 否 1是
     *
     * @param unlimited 是否无限流量 0 否 1是
     */
    public void setUnlimited(Boolean unlimited) {
        this.unlimited = unlimited;
    }

    /**
     * 获取最大流量
     *
     * @return limited - 最大流量
     */
    public Integer getLimited() {
        return limited;
    }

    /**
     * 设置最大流量
     *
     * @param limited 最大流量
     */
    public void setLimited(Integer limited) {
        this.limited = limited;
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
     * @return product_name_en
     */
    public String getProductNameEn() {
        return productNameEn;
    }

    /**
     * @param productNameEn
     */
    public void setProductNameEn(String productNameEn) {
        this.productNameEn = productNameEn;
    }

    /**
     * @return price_en
     */
    public BigDecimal getPriceEn() {
        return priceEn;
    }

    /**
     * @param priceEn
     */
    public void setPriceEn(BigDecimal priceEn) {
        this.priceEn = priceEn;
    }

    /**
     * @return description_en
     */
    public String getDescriptionEn() {
        return descriptionEn;
    }

    /**
     * @param descriptionEn
     */
    public void setDescriptionEn(String descriptionEn) {
        this.descriptionEn = descriptionEn;
    }

    /**
     * 获取是否送流量
     *
     * @return gifted - 是否送流量
     */
    public Boolean getGifted() {
        return gifted;
    }

    /**
     * 设置是否送流量
     *
     * @param gifted 是否送流量
     */
    public void setGifted(Boolean gifted) {
        this.gifted = gifted;
    }

    /**
     * 获取流量大小
     *
     * @return limit_num - 流量大小
     */
    public Integer getLimitNum() {
        return limitNum;
    }

    /**
     * 设置流量大小
     *
     * @param limitNum 流量大小
     */
    public void setLimitNum(Integer limitNum) {
        this.limitNum = limitNum;
    }

    /**
     * @return gift_msg
     */
    public String getGiftMsg() {
        return giftMsg;
    }

    /**
     * @param giftMsg
     */
    public void setGiftMsg(String giftMsg) {
        this.giftMsg = giftMsg;
    }

    /**
     * @return gift_msg_en
     */
    public String getGiftMsgEn() {
        return giftMsgEn;
    }

    /**
     * @param giftMsgEn
     */
    public void setGiftMsgEn(String giftMsgEn) {
        this.giftMsgEn = giftMsgEn;
    }
}