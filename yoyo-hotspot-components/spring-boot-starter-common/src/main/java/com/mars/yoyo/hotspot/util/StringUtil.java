package com.mars.yoyo.hotspot.util;

import org.apache.commons.lang3.StringUtils;

import java.util.Random;

/**
 * @author tookbra
 * @date 2018/4/6
 * @description
 */
public class StringUtil extends StringUtils {


    private static final String SMS_CODES = "123456789";

    /**
     * 异或
     * @param datas
     * @return
     */
    public static byte getXor(byte[] datas){
        if(datas.length > 0) {
            byte temp = datas[0];

            for (int i = 1; i < datas.length; i++) {
                temp ^= datas[i];
            }
            return temp;
        }
        return 0;
    }

    /**
     * 生成随机数
     * @return 随机数
     */
    public static String generateRandomCode(int length) {
        return getRandom(SMS_CODES, length);
    }

    /**
     * 生成随机
     * @param randStr 因子
     * @param length 随机数长度
     * @return 随机数
     */
    public static String getRandom(String randStr, int length) {

        String rand = "";

        Random random = new Random();
        String c = "";
        for (int i = 1; i <= length; i++) {
            c = String.valueOf(randStr.charAt(random.nextInt(randStr.length())));
            rand += c;
        }

        return rand;
    }

    /**
     * 对象转字符串
     * @param obj 对象
     * @return 字符串
     */
    public static String objectToEmpty(Object obj) {
        return obj == null ? "" : obj.toString();
    }
}
