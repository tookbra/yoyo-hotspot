package com.mars.yoyo.hotspot.admin.enums;

/**
 * 支付渠道
 *
 * @author admin
 * @create 2018/5/23
 */
public enum PayChannelEnum {

    BALANCE(1, "账户余额"),

    WECHAT(2, "微信支付"),

    ALIPAY(3, "支付宝"),

    PAYPAL(4, "paypal"),

    ;

    private Integer code;

    private String message;

    PayChannelEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static PayChannelEnum getByCode(int code) {
        for (PayChannelEnum type : values()) {
            if (type.getCode() == (code)) {
                return type;
            }
        }
        return null;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
