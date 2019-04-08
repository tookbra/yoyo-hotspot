package com.mars.yoyo.hotspot.admin.enums;

/**
 * 订单类型
 *
 * @author admin
 * @create 2018/5/23
 */
public enum OrderTypeEnum {

    DEPOSIT(1, "押金"),

    RECHARGE(2, "充值"),

    RENT(3, "租借"),

    ;


    private Integer code;

    private String message;

    OrderTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static OrderTypeEnum getByCode(int code) {
        for (OrderTypeEnum type : values()) {
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
