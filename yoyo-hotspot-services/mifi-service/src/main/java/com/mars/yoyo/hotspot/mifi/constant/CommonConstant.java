package com.mars.yoyo.hotspot.mifi.constant;

/**
 * @author tookbra
 * @date 2018/5/17
 * @description
 */
public class CommonConstant {

    /**
     * 绑定手机
     */
    public static final int COUPON_BIND_PHONE_CHANNEL = 1;
    /**
     * 账户余额支付
     */
    public static final int ORDER_CHANNEL_BALANCE = 1;
    /**
     * 微信支付
     */
    public static final int ORDER_CHANNEL_WECHAT = 2;
    /**
     * 支付宝支付
     */
    public static final int ORDER_CHANNEL_ALI = 3;
    /**
     * 订单类型 押金
     */
    public static final int ORDER_TYPE_DEPOSIT = 1;
    /**
     * 订单类型 充值
     */
    public static final int ORDER_TYPE_RECHARGE = 2;
    /**
     * 订单类型 租借
     */
    public static final int ORDER_TYPE_RENT = 3;
    /**
     * 订单类型 续费
     */
    public static final int ORDER_TYPE_RENEWS = 4;
    /**
     * 订单类型 归还
     */
    public static final int ORDER_TYPE_RETURN = 5;

    /** 小时借 **/
    public static final int PRODUCT_TYPE_HOUR = 1;
    /** 包天借 **/
    public static final int PRODUCT_TYPE_DAY = 2;
    /** 包月借 **/
    public static final int PRODUCT_TYPE_MONTH = 3;
    /** 处理中 **/
    public static final int ORDER_STATE_PROCESSING = 1;
    /** 成功 **/
    public static final int ORDER_STATE_SUCCESS = 2;
    /** 失败 **/
    public static final int ORDER_STATE_FAILED = 3;

    /** 归还 **/
    public static final int RENT_RETURNED = 1;
    /** 未归还 **/
    public static final int RENT_NOT_RETURNED = 0;

    public static final String LANG_EN = "en";
    public static final String LANG_CH = "zh_CN";

    /** 未登录 **/
    public static final int CODE_NOT_LOGIN = 9999;

    /** 押金未支付 **/
    public static final int CODE_DEPOSIT_NOT_PAY = 2000;
    /** 暂无租借记录 **/
    public static final int CODE_RENT_CURRENT_NOT_FOUND = 2100;
    public static final int PAY_WECHAT_NOT_FOUND = 3000;
    public static final int DEVICE_NOT_FOUND = 4000;
    public static final int DEVICE_ITEM_NOT_FOUND = 4001;

    /** 注册短信 **/
    public static final String SMS_TYPE_REG = "REG";
    public static final String SMS_TYPE_REG_EN = "REG_EN";
    /** 归还短信 **/
    public static final String SMS_TYPE_RETURN = "RETURN";
    public static final String SMS_TYPE_EXPIRATION_REMINDER = "MATURITY";
    /**
     * 小时借归还
     */
    public static final String SMS_TYPE_RETURN_HOUR = "RETURN_HOUR";
    public static final String SMS_TYPE_RETURN_HOUR_EN = "RETURN_HOUR_EN";
    /**
     * 包天/月到期未超时
     */
    public static final String SMS_TYPE_RETURN_NOT_TIME_OUT = "RETURN_NOT_TIME_OUT";
    public static final String SMS_TYPE_RETURN_NOT_TIME_OUT_EN = "RETURN_NOT_TIME_OUT_EN";
    /**
     * 包天/月到期并超时
     */
    public static final String SMS_TYPE_RETURN_TIME_OUT = "RETURN_TIME_OUT";
    public static final String SMS_TYPE_RETURN_TIME_OUT_EN = "RETURN_TIME_OUT_EN";
    /**
     * 包天/月已经到期
     */
    public static final String SMS_TYPE_RENT_MATURITY = "RENT_MATURITY";
    public static final String SMS_TYPE_RENT_MATURITY_EN = "RENT_MATURITY_EN";
    /**
     * 包天/月即将到期
     */
    public static final String SMS_TYPE_RENT_EXPIRING = "RENT_EXPIRING";
    public static final String SMS_TYPE_RENT_EXPIRING_EN = "RENT_EXPIRING_EN";
    /**
     * 小时封顶提醒
     */
    public static final String SMS_TYPE_RENT_ARREARS = "RENT_ARREARS";
    public static final String SMS_TYPE_RENT_ARREARS_EN = "RENT_ARREARS_EN";

    public static final String SMS_TYPE_LEASE = "LEASE";
    public static final String SMS_TYPE_LEASE_EN = "LEASE_EN";
}
