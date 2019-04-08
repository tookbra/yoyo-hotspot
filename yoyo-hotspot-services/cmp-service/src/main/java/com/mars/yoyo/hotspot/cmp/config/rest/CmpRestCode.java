package com.mars.yoyo.hotspot.cmp.config.rest;

import java.util.Arrays;

/**
 * @author tookbra
 * @date 2018/4/8
 * @description
 */
public enum CmpRestCode {
    SUCCESS(0, "操作成功"),
    FAILED(-1, "操作失败"),
    SIGN_ERROR(-2, "签名错误"),
    ID_VALIDATE_FAILED(-3, "IP验证失败"),
    PRODUCT_CODE_ILLEGAL(-4, "产品编码不存在"),
    RATE_IS_NOT_CONFIGURED(-5, "费率未配置"),
    DUPLICATE_ORDERS(-6, "订单重复"),
    CARD_NOT_EXIST(-7, "卡号不存在"),
    INSUFFICIENT_BALANCE(-8, "余额不足"),
    DEDUCTIONS_FAILED(-9, "扣费失败"),
    ORDER_NOT_EXIST(-10, "订单不存在"),
    OTHER_ERROR(-9999, "未知错误");

    int code;
    String msg;

    CmpRestCode(int code, String msg) {
        this.code = code;
        this.code = code;
    }

    public static CmpRestCode of(int code) {
        return Arrays.stream(CmpRestCode.values()).filter(v -> v.code == code).findFirst().orElse(OTHER_ERROR);
    }
}
