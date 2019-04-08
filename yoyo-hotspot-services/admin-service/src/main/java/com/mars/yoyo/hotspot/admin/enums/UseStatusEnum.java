package com.mars.yoyo.hotspot.admin.enums;

/**
 * 使用的状态枚举
 *
 * @author admin
 * @create 2018/5/16
 * @since 1.0.0
 */
public enum UseStatusEnum {

    UNUSED(0, "未使用"),

    USED(1, "已使用"),

    ;

    private Integer code;

    private String message;

    UseStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static UseStatusEnum getByCode(int code) {
        for (UseStatusEnum type : values()) {
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
