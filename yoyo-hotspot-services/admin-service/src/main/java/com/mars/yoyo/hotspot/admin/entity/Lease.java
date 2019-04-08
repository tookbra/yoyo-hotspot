package com.mars.yoyo.hotspot.admin.entity;

import java.util.Date;
import javax.persistence.*;

public class Lease {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private Integer userId;

    /**
     * 机柜id
     */
    @Column(name = "device_id")
    private String deviceId;

    /**
     * 设备id
     */
    @Column(name = "power_bank_id")
    private String powerBankId;

    /**
     * 产品id
     */
    @Column(name = "product_id")
    private Integer productId;

    /**
     * 产品类型
     */
    @Column(name = "product_type")
    private Integer productType;

    /**
     * 产品名称
     */
    @Column(name = "product_name")
    private String productName;

    /**
     * 密码
     */
    private String passowrd;

    /**
     * 租赁单号
     */
    @Column(name = "lease_no")
    private String leaseNo;

    /**
     * 模式转换 上一个租借编号
     */
    @Column(name = "last_lease_no")
    private String lastLeaseNo;

    /**
     * 订单号
     */
    @Column(name = "order_no")
    private String orderNo;

    /**
     * 租赁时间
     */
    @Column(name = "rent_time")
    private Date rentTime;

    /**
     * 是否归还 0否 1是
     */
    private Integer returned;

    /**
     * 归还时间
     */
    @Column(name = "return_time")
    private Date returnTime;

    /**
     * 预计过期时间
     */
    @Column(name = "expected_return_time")
    private Date expectedReturnTime;

    /**
     * 是否国际版
     */
    @Column(name = "lang_en")
    private Integer langEn;

    /**
     * 是否结束租赁 0 否 1 是
     */
    private Integer overed;

    /**
     * 租赁地址
     */
    private String address;

    /**
     * 设备状态 0 初始 1成功 2失败
     */
    private Integer state;

    /**
     * 到期短信是否发送 0否 1是
     */
    @Column(name = "expired_sms")
    private Integer expiredSms;

    /**
     * 即将到期短信是否发送 0否 1是
     */
    @Column(name = "sms_expiring")
    private Integer smsExpiring;

    /**
     * 封顶短信提醒 0否 1是
     */
    @Column(name = "sms_cap")
    private Integer smsCap;

    /**
     * 槽位号
     */
    private Integer slot;

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
     * 获取机柜id
     *
     * @return device_id - 机柜id
     */
    public String getDeviceId() {
        return deviceId;
    }

    /**
     * 设置机柜id
     *
     * @param deviceId 机柜id
     */
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    /**
     * 获取设备id
     *
     * @return power_bank_id - 设备id
     */
    public String getPowerBankId() {
        return powerBankId;
    }

    /**
     * 设置设备id
     *
     * @param powerBankId 设备id
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
     * 获取产品类型
     *
     * @return product_type - 产品类型
     */
    public Integer getProductType() {
        return productType;
    }

    /**
     * 设置产品类型
     *
     * @param productType 产品类型
     */
    public void setProductType(Integer productType) {
        this.productType = productType;
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
     * 获取密码
     *
     * @return passowrd - 密码
     */
    public String getPassowrd() {
        return passowrd;
    }

    /**
     * 设置密码
     *
     * @param passowrd 密码
     */
    public void setPassowrd(String passowrd) {
        this.passowrd = passowrd;
    }

    /**
     * 获取租赁单号
     *
     * @return lease_no - 租赁单号
     */
    public String getLeaseNo() {
        return leaseNo;
    }

    /**
     * 设置租赁单号
     *
     * @param leaseNo 租赁单号
     */
    public void setLeaseNo(String leaseNo) {
        this.leaseNo = leaseNo;
    }

    /**
     * 获取模式转换 上一个租借编号
     *
     * @return last_lease_no - 模式转换 上一个租借编号
     */
    public String getLastLeaseNo() {
        return lastLeaseNo;
    }

    /**
     * 设置模式转换 上一个租借编号
     *
     * @param lastLeaseNo 模式转换 上一个租借编号
     */
    public void setLastLeaseNo(String lastLeaseNo) {
        this.lastLeaseNo = lastLeaseNo;
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
     * 获取租赁时间
     *
     * @return rent_time - 租赁时间
     */
    public Date getRentTime() {
        return rentTime;
    }

    /**
     * 设置租赁时间
     *
     * @param rentTime 租赁时间
     */
    public void setRentTime(Date rentTime) {
        this.rentTime = rentTime;
    }

    /**
     * 获取是否归还 0否 1是
     *
     * @return returned - 是否归还 0否 1是
     */
    public Integer getReturned() {
        return returned;
    }

    /**
     * 设置是否归还 0否 1是
     *
     * @param returned 是否归还 0否 1是
     */
    public void setReturned(Integer returned) {
        this.returned = returned;
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
     * 获取预计过期时间
     *
     * @return expected_return_time - 预计过期时间
     */
    public Date getExpectedReturnTime() {
        return expectedReturnTime;
    }

    /**
     * 设置预计过期时间
     *
     * @param expectedReturnTime 预计过期时间
     */
    public void setExpectedReturnTime(Date expectedReturnTime) {
        this.expectedReturnTime = expectedReturnTime;
    }

    /**
     * 获取是否国际版
     *
     * @return lang_en - 是否国际版
     */
    public Integer getLangEn() {
        return langEn;
    }

    /**
     * 设置是否国际版
     *
     * @param langEn 是否国际版
     */
    public void setLangEn(Integer langEn) {
        this.langEn = langEn;
    }

    /**
     * 获取是否结束租赁 0 否 1 是
     *
     * @return overed - 是否结束租赁 0 否 1 是
     */
    public Integer getOvered() {
        return overed;
    }

    /**
     * 设置是否结束租赁 0 否 1 是
     *
     * @param overed 是否结束租赁 0 否 1 是
     */
    public void setOvered(Integer overed) {
        this.overed = overed;
    }

    /**
     * 获取租赁地址
     *
     * @return address - 租赁地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置租赁地址
     *
     * @param address 租赁地址
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 获取设备状态 0 初始 1成功 2失败
     *
     * @return state - 设备状态 0 初始 1成功 2失败
     */
    public Integer getState() {
        return state;
    }

    /**
     * 设置设备状态 0 初始 1成功 2失败
     *
     * @param state 设备状态 0 初始 1成功 2失败
     */
    public void setState(Integer state) {
        this.state = state;
    }

    /**
     * 获取到期短信是否发送 0否 1是
     *
     * @return expired_sms - 到期短信是否发送 0否 1是
     */
    public Integer getExpiredSms() {
        return expiredSms;
    }

    /**
     * 设置到期短信是否发送 0否 1是
     *
     * @param expiredSms 到期短信是否发送 0否 1是
     */
    public void setExpiredSms(Integer expiredSms) {
        this.expiredSms = expiredSms;
    }

    /**
     * 获取即将到期短信是否发送 0否 1是
     *
     * @return sms_expiring - 即将到期短信是否发送 0否 1是
     */
    public Integer getSmsExpiring() {
        return smsExpiring;
    }

    /**
     * 设置即将到期短信是否发送 0否 1是
     *
     * @param smsExpiring 即将到期短信是否发送 0否 1是
     */
    public void setSmsExpiring(Integer smsExpiring) {
        this.smsExpiring = smsExpiring;
    }

    /**
     * 获取封顶短信提醒 0否 1是
     *
     * @return sms_cap - 封顶短信提醒 0否 1是
     */
    public Integer getSmsCap() {
        return smsCap;
    }

    /**
     * 设置封顶短信提醒 0否 1是
     *
     * @param smsCap 封顶短信提醒 0否 1是
     */
    public void setSmsCap(Integer smsCap) {
        this.smsCap = smsCap;
    }

    /**
     * 获取槽位号
     *
     * @return slot - 槽位号
     */
    public Integer getSlot() {
        return slot;
    }

    /**
     * 设置槽位号
     *
     * @param slot 槽位号
     */
    public void setSlot(Integer slot) {
        this.slot = slot;
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