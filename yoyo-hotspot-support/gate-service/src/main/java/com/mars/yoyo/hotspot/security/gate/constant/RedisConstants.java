package com.mars.yoyo.hotspot.security.gate.constant;

import java.text.MessageFormat;

/**
 * @author tookbra
 * @date 2018/5/25
 * @description
 */
public class RedisConstants {

    public static final String NONCE_PRE = "user:{0}:nonce:{1}";

    public static String getNonceKey(int userId, String nonce) {
        return MessageFormat.format(NONCE_PRE, userId, nonce);
    }
}
