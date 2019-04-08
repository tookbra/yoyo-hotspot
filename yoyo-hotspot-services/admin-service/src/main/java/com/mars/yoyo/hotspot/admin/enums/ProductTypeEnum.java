package com.mars.yoyo.hotspot.admin.enums;

/**
 * 产品套餐枚举
 *
 * @author admin
 * @create 2018/5/21
 */
public enum ProductTypeEnum {

    HOURS(1, "小时借"),

    DAYS(2, "包天借"),

    WEEKS(3, "包周借"),

    MONTHS(4, "包月借")

    ;

    private Integer code;

    private String message;

    ProductTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ProductTypeEnum getByCode(int code) {
        for (ProductTypeEnum type : values()) {
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
