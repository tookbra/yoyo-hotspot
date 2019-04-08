package com.mars.yoyo.hotspot.util;

import java.math.BigDecimal;

/**
 * @author tookbra
 * @date 2018/5/22
 * @description
 */
public class NumberUtil {

    /**
     * 除法精度
     */
    private static final int DIV_SCALE = 2;

    private static final BigDecimal DIV_YUAN = new BigDecimal(100);

    /**
     * 将分转为元
     *
     * @param money 金额 分
     * @return 元
     */
    public static BigDecimal centToYuan(BigDecimal money) {
        return div(money, DIV_YUAN);
    }

    /**
     * 分转元
     * @param money 金额 分
     * @return 元
     */
    public static BigDecimal centToYuan(String money) {
        return div(new BigDecimal(money), DIV_YUAN);
    }

    /**
     * 元转分
     * @param money 金额
     * @return 分
     */
    public static BigDecimal yuanToCent(int money) {
        return yuanToCent(new BigDecimal(money));
    }

    /**
     * 元转分
     * @param money 金额
     * @return 分
     */
    public static BigDecimal yuanToCent(BigDecimal money) {
        return money.multiply(DIV_YUAN);
    }

    /**
     * 除法 默认精确10位
     *
     * @param s1 除数
     * @param s2 被除数
     * @return
     */
    public static BigDecimal div(BigDecimal s1, BigDecimal s2) {
        return div(s1, s2, DIV_SCALE);
    }

    /**
     * 除法
     *
     * @param s1    除数
     * @param s2    被除数
     * @param scale 精确位数
     * @return
     */
    public static BigDecimal div(BigDecimal s1, BigDecimal s2, int scale) {
        return s1.divide(s2, scale, BigDecimal.ROUND_HALF_UP);
    }

}
