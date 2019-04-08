package com.mars.yoyo.hotspot.mifi.enums;

import java.util.Arrays;

/**
 * @author tookbra
 * @date 2018/5/22
 * @description
 */
public enum  ProductTypeEnum {
    HOUR(1, "小时借", "timing"),
    DAY(2, "包天借", "daily"),
    MONTH(3, "包月借", "monthly");

    Integer type;
    String msg;
    String msgEn;

    ProductTypeEnum(Integer type, String msg, String msgEn) {
        this.type = type;
        this.msg = msg;
        this.msgEn = msgEn;
    }

    public Integer getType() {
        return type;
    }

    public String getMsg() {
        return msg;
    }

    public String getMsgEn() {
        return msgEn;
    }

    public static ProductTypeEnum getMsgEn(int type) {
        return Arrays.stream(ProductTypeEnum.values())
                .filter(e -> e.type.equals(type))
                .findFirst().get();
    }

    public static void main(String[] args) {
        System.out.println(ProductTypeEnum.getMsgEn(2).getMsgEn());
    }
}
