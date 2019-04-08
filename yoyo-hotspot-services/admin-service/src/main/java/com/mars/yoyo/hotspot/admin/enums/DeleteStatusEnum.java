package com.mars.yoyo.hotspot.admin.enums;

/**
 * 删除状态枚举
 *
 * @author admin
 * @create 2018/5/17
 * @since 1.0.0
 */
public enum DeleteStatusEnum {

    UNDELETE(0, "未删除"),

    DELETED(1, "已删除"),

    ;

    private Integer code;

    private String message;

    DeleteStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static DeleteStatusEnum getByCode(int code) {
        for (DeleteStatusEnum type : values()) {
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
