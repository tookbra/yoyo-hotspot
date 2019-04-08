package com.mars.yoyo.hotspot.security.gate.util;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @author tookbra
 * @date 2018/5/25
 * @description
 */
public class SignUtil {

    public static String createSign(Map<String, String[]> params, String token, String timestamp, String nonce, String url) {
        SortedMap<String, String> sortedMap = new TreeMap(params);
        StringBuilder toSign = new StringBuilder();
        Iterator iterator = sortedMap.keySet().iterator();
        String sign = "";
        while (iterator.hasNext()) {
            String key = (String)iterator.next();
            String [] valueArray = (String[])params.get(key);
            for (String value : valueArray) {
                toSign.append(key).append("=").append(value).append("&");
            }
            sign = toSign.substring(0, toSign.lastIndexOf("&"));
        }
        return DigestUtils.md5Hex(sign + token + timestamp + nonce + url);
    }
}
