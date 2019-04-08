package com.mars.yoyo.hotspot.mifi.domain;

import java.util.Date;
import javax.persistence.*;

public class Rent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private Integer userId;

    /**
     * 设备id
     */
    @Column(name = "device_id")
    private String deviceId;

    /**
     * 充电宝id
     */
    @Column(name = "power_bank_id")
    private String powerBankId;

    /**
     * 产品id
     */
    @Column(name = "product_id")
    private Integer productId;

    @Column(name = "product_name")
    private String productName;

    /**
     * wifi密码
     */
    private String passowrd;

    /**
     * 租借编号
     */
    @Column(name = "rent_no")
    private String rentNo;

    /**
     * 订单号
     */
    @Column(name = "order_no")
    private String orderNo;

    /**
     * 租借时间
     */
    @Column(name = "rent_time")
    private Date rentTime;

    /**
     * 是否已归还 1是 0否
     */
    private Boolean returned;

    /**
     * 租借地址
     */
    private String address;

    /**
     * 归还时间
     */
    @Column(name = "return_time")
    private Date returnTime;

    /**
     * 预计归还时间
     */
    @Column(name = "expected_return_time")
    private Date expectedReturnTime;

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
     * 0租借中 1成功 2失败
     */
    private Integer state;

    /**
     * 槽位
     */
    private Short slot;

    /**
     * 是否国际版
     */
    @Column(name = "lang_en")
    private Boolean langEn;

    /**
     * 0 初始 1超时未支付 2超时已支付
     */
    @Column(name = "expected_state")
    private Integer expectedState;

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
     * 获取设备id
     *
     * @return device_id - 设备id
     */
    public String getDeviceId() {
        return deviceId;
    }

    /**
     * 设置设备id
     *
     * @param deviceId 设备id
     */
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    /**
     * 获取充电宝id
     *
     * @return power_bank_id - 充电宝id
     */
    public String getPowerBankId() {
        return powerBankId;
    }

    /**
     * 设置充电宝id
     *
     * @param powerBankId 充电宝id
     */
    public void setPowerBankId(String powerBankId) {
        this.powerBankId = powerBankId;
    }

    /**
     * 获取产品id
     *
     * @return product_id - 产品id
     */
    public Integer getProductId() {
        return productId;
    }

    /**
     * 设置产品id
     *
     * @param productId 产品id
     */
    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    /**
     * @return product_name
     */
    public String getProductName() {
        return productName;
    }

    /**
     * @param productName
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     * 获取wifi密码
     *
     * @return passowrd - wifi密码
     */
    public String getPassowrd() {
        return passowrd;
    }

    /**
     * 设置wifi密码
     *
     * @param passowrd wifi密码
     */
    public void setPassowrd(String passowrd) {
        this.passowrd = passowrd;
    }

    /**
     * 获取租借编号
     *
     * @return rent_no - 租借编号
     */
    public String getRentNo() {
        return rentNo;
    }

    /**
     * 设置租借编号
     *
     * @param rentNo 租借编号
     */
    public void setRentNo(String rentNo) {
        this.rentNo = rentNo;
    }

    /**
     * 获取订单号
     *
     * @return order_no - 订单号
     */
    public String getOrderNo() {
        return orderNo;
    }

    /**
     * 设置订单号
     *
     * @param orderNo 订单号
     */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    /**
     * 获取租借时间
     *
     * @return rent_time - 租借时间
     */
    public Date getRentTime() {
        return rentTime;
    }

    /**
     * 设置租借时间
     *
     * @param rentTime 租借时间
     */
    public void setRentTime(Date rentTime) {
        this.rentTime = rentTime;
    }

    /**
     * 获取是否已归还 1是 0否
     *
     * @return returned - 是否已归还 1是 0否
     */
    public Boolean getReturned() {
        return returned;
    }

    /**
     * 设置是否已归还 1是 0否
     *
     * @param returned 是否已归还 1是 0否
     */
    public void setReturned(Boolean returned) {
        this.returned = returned;
    }

    /**
     * 获取租借地址
     *
     * @return address - 租借地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置租借地址
     *
     * @param address 租借地址
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 获取归还时间
     *
     * @return return_time - 归还时间
     */
    public Date getReturnTime() {
        return returnTime;
    }

    /**
     * 设置归还时间
     *
     * @param returnTime 归还时间
     */
    public void setReturnTime(Date returnTime) {
        this.returnTime = returnTime;
    }

    /**
     * 获取预计归还时间
     *
     * @return expected_return_time - 预计归还时间
     */
    public Date getExpectedReturnTime() {
        return expectedReturnTime;
    }

    /**
     * 设置预计归还时间
     *
     * @param expectedReturnTime 预计归还时间
     */
    public void setExpectedReturnTime(Date expectedReturnTime) {
        this.expectedReturnTime = expectedReturnTime;
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
     * 获取0租借中 1成功 2失败
     *
     * @return state - 0租借中 1成功 2失败
     */
    public Integer getState() {
        return state;
    }

    /**
     * 设置0租借中 1成功 2失败
     *
     * @param state 0租借中 1成功 2失败
     */
    public void setState(Integer state) {
        this.state = state;
    }

    /**
     * 获取槽位
     *
     * @return slot - 槽位
     */
    public Short getSlot() {
        return slot;
    }

    /**
     * 设置槽位
     *
     * @param slot 槽位
     */
    public void setSlot(Short slot) {
        this.slot = slot;
    }

    /**
     * 获取是否国际版
     *
     * @return lang_en - 是否国际版
     */
    public Boolean getLangEn() {
        return langEn;
    }

    /**
     * 设置是否国际版
     *
     * @param langEn 是否国际版
     */
    public void setLangEn(Boolean langEn) {
        this.langEn = langEn;
    }

    /**
     * 获取0 初始 1超时未支付 2超时已支付
     *
     * @return expected_state - 0 初始 1超时未支付 2超时已支付
     */
    public Integer getExpectedState() {
        return expectedState;
    }

    /**
     * 设置0 初始 1超时未支付 2超时已支付
     *
     * @param expectedState 0 初始 1超时未支付 2超时已支付
     */
    public void setExpectedState(Integer expectedState) {
        this.expectedState = expectedState;
    }
}