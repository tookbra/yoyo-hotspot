package com.mars.yoyo.hotspot.mifi.domain;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "pay_order")
public class PayOrder {
    @Id
    @Column(name = "order_id")
    private String orderId;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private Integer userId;

    /**
     * 支付渠道 1 账户余额 2微信支付 3支付宝 4paypal
     */
    @Column(name = "pay_channel")
    private Integer payChannel;

    /**
     * 支付金额
     */
    private BigDecimal amount;

    /**
     * 币种 默认人民币
     */
    private String currency;

    /**
     * 订单类型 1押金 2充值 3租借
     */
    @Column(name = "order_type")
    private Integer orderType;

    /**
     * 状态，0订单生成 1支付中 2支付成功 3支付失败
     */
    private Integer state;

    /**
     * 支付ip
     */
    @Column(name = "pay_ip")
    private String payIp;

    /**
     * 支付设备
     */
    private String device;

    /**
     * 商品描述信息
     */
    private String body;

    /**
     * 参数
     */
    private String ext;

    /**
     * 支付错误码
     */
    @Column(name = "error_code")
    private String errorCode;

    /**
     * 支付错误信息
     */
    @Column(name = "error_msg")
    private String errorMsg;

    /**
     * 通知地址
     */
    @Column(name = "notify_url")
    private String notifyUrl;

    /**
     * 通知次数
     */
    @Column(name = "notify_count")
    private Integer notifyCount;

    /**
     * 订单失效时间
     */
    @Column(name = "expire_time")
    private Date expireTime;

    /**
     * 支付成功时间
     */
    @Column(name = "pay_success_time")
    private Date paySuccessTime;

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
     * 第三方订单号
     */
    @Column(name = "payment_id")
    private String paymentId;

    /**
     * 备注
     */
    private String remark;

    /**
     * 优惠券id
     */
    @Column(name = "user_coupon_id")
    private Integer userCouponId;

    /**
     * @return order_id
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * @param orderId
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
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
     * 获取支付渠道 1 账户余额 2微信支付 3支付宝 4paypal
     *
     * @return pay_channel - 支付渠道 1 账户余额 2微信支付 3支付宝 4paypal
     */
    public Integer getPayChannel() {
        return payChannel;
    }

    /**
     * 设置支付渠道 1 账户余额 2微信支付 3支付宝 4paypal
     *
     * @param payChannel 支付渠道 1 账户余额 2微信支付 3支付宝 4paypal
     */
    public void setPayChannel(Integer payChannel) {
        this.payChannel = payChannel;
    }

    /**
     * 获取支付金额
     *
     * @return amount - 支付金额
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * 设置支付金额
     *
     * @param amount 支付金额
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * 获取币种 默认人民币
     *
     * @return currency - 币种 默认人民币
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * 设置币种 默认人民币
     *
     * @param currency 币种 默认人民币
     */
    public void setCurrency(String currency) {
        this.currency = currency;
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
     * 获取状态，0订单生成 1支付中 2支付成功 3支付失败
     *
     * @return state - 状态，0订单生成 1支付中 2支付成功 3支付失败
     */
    public Integer getState() {
        return state;
    }

    /**
     * 设置状态，0订单生成 1支付中 2支付成功 3支付失败
     *
     * @param state 状态，0订单生成 1支付中 2支付成功 3支付失败
     */
    public void setState(Integer state) {
        this.state = state;
    }

    /**
     * 获取支付ip
     *
     * @return pay_ip - 支付ip
     */
    public String getPayIp() {
        return payIp;
    }

    /**
     * 设置支付ip
     *
     * @param payIp 支付ip
     */
    public void setPayIp(String payIp) {
        this.payIp = payIp;
    }

    /**
     * 获取支付设备
     *
     * @return device - 支付设备
     */
    public String getDevice() {
        return device;
    }

    /**
     * 设置支付设备
     *
     * @param device 支付设备
     */
    public void setDevice(String device) {
        this.device = device;
    }

    /**
     * 获取商品描述信息
     *
     * @return body - 商品描述信息
     */
    public String getBody() {
        return body;
    }

    /**
     * 设置商品描述信息
     *
     * @param body 商品描述信息
     */
    public void setBody(String body) {
        this.body = body;
    }

    /**
     * 获取参数
     *
     * @return ext - 参数
     */
    public String getExt() {
        return ext;
    }

    /**
     * 设置参数
     *
     * @param ext 参数
     */
    public void setExt(String ext) {
        this.ext = ext;
    }

    /**
     * 获取支付错误码
     *
     * @return error_code - 支付错误码
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * 设置支付错误码
     *
     * @param errorCode 支付错误码
     */
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * 获取支付错误信息
     *
     * @return error_msg - 支付错误信息
     */
    public String getErrorMsg() {
        return errorMsg;
    }

    /**
     * 设置支付错误信息
     *
     * @param errorMsg 支付错误信息
     */
    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    /**
     * 获取通知地址
     *
     * @return notify_url - 通知地址
     */
    public String getNotifyUrl() {
        return notifyUrl;
    }

    /**
     * 设置通知地址
     *
     * @param notifyUrl 通知地址
     */
    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    /**
     * 获取通知次数
     *
     * @return notify_count - 通知次数
     */
    public Integer getNotifyCount() {
        return notifyCount;
    }

    /**
     * 设置通知次数
     *
     * @param notifyCount 通知次数
     */
    public void setNotifyCount(Integer notifyCount) {
        this.notifyCount = notifyCount;
    }

    /**
     * 获取订单失效时间
     *
     * @return expire_time - 订单失效时间
     */
    public Date getExpireTime() {
        return expireTime;
    }

    /**
     * 设置订单失效时间
     *
     * @param expireTime 订单失效时间
     */
    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    /**
     * 获取支付成功时间
     *
     * @return pay_success_time - 支付成功时间
     */
    public Date getPaySuccessTime() {
        return paySuccessTime;
    }

    /**
     * 设置支付成功时间
     *
     * @param paySuccessTime 支付成功时间
     */
    public void setPaySuccessTime(Date paySuccessTime) {
        this.paySuccessTime = paySuccessTime;
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
     * 获取第三方订单号
     *
     * @return payment_id - 第三方订单号
     */
    public String getPaymentId() {
        return paymentId;
    }

    /**
     * 设置第三方订单号
     *
     * @param paymentId 第三方订单号
     */
    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    /**
     * 获取备注
     *
     * @return remark - 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置备注
     *
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 获取优惠券id
     *
     * @return user_coupon_id - 优惠券id
     */
    public Integer getUserCouponId() {
        return userCouponId;
    }

    /**
     * 设置优惠券id
     *
     * @param userCouponId 优惠券id
     */
    public void setUserCouponId(Integer userCouponId) {
        this.userCouponId = userCouponId;
    }
}