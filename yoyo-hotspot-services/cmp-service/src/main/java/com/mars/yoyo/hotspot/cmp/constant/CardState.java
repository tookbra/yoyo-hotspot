package com.mars.yoyo.hotspot.cmp.constant;

/**
 * @author tookbra
 * @date 2018/5/11
 * @description
 */
public enum CardState {

    NORMAL("00", "正常"),
    UNIDIRECTIONAL_DOWNTIME("01", "单向停机"),
    DOWNTIME("02", "停机"),
    PRE_ACCOUNT_CANCELLATION("03", "预销号"),
    ACCOUNT_CANCELLATION("04", "销号/拆机"),
    TRANSFER("05", "过户"),
    DORMANCY("06", "休眠"),
    TO_BE_ACTIVATED("07", "待激"),
    TERMINATED("08", "已停用"),
    STOCK("09", "库存"),
    EXPIRED("10", "已失效"),
    DOWNTIME_VIOLATION("11", "违章停机"),
    REGISTER_LOSS("12", "挂失"),
    USER_STOPPED("13", "用户报停"),
    BROKEN_NETWORK("14", "表示断网"),
    DOES_NOT_EXIST("99", "不存在");

    String code;
    String msg;

    CardState(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
