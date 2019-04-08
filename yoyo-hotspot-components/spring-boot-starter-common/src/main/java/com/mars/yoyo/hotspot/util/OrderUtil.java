package com.mars.yoyo.hotspot.util;

import java.util.Date;

/**
 * @author tookbra
 * @date 2018/5/21
 * @description
 */
public class OrderUtil {

    public static String getOrderNo() {
        String date = DateUtil.formatPureDateTime(new Date());
        return date;
    }
}
