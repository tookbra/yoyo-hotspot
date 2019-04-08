package com.mars.yoyo.hotspot.admin.enums;

/**
 * 订单状态
 *
 * @author admin
 * @create 2018/5/25
 */
public enum OrderStateEnum {

    GENERATE(0, "订单生成"),

    PAYING(1, "支付中"),

    SUCCESS(2, "支付成功"),

    FAILURE(3, "支付失败")

    ;

    private Integer code;

    private String message;

    OrderStateEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static OrderStateEnum getByCode(int code) {
        for (OrderStateEnum type : values()) {
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
