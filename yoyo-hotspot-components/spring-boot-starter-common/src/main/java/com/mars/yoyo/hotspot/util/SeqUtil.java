package com.mars.yoyo.hotspot.util;

import java.util.Date;

/**
 * @author tookbra
 * @date 2018/5/24
 * @description
 */
public class SeqUtil {

    public static String generatorId(int userId) {
        Date date = new Date();
        String orderId = null;
        try {
            orderId = DateUtil.formatPureDateTime(date).concat(String.valueOf(userId)).concat(StringUtil.generateRandomCode(4));
            System.out.println(1);
        } catch(Exception ex) {
            ex.printStackTrace();
            System.out.println("订单号生成失败");
        }
        if (StringUtil.isBlank(orderId)) {
            return null;
        }
        return orderId;
    }
}
