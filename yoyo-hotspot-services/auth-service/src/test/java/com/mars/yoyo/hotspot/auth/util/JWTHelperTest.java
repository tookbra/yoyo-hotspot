package com.mars.yoyo.hotspot.auth.util;

import org.joda.time.DateTime;
import org.junit.Test;

/**
 * @author tookbra
 * @date 2018/7/2
 * @description
 */
public class JWTHelperTest {

    @Test
    public void expireTest() {
        System.out.println(DateTime.now().toDate());
        System.out.println(DateTime.now().plusSeconds(2592000).toDate());

    }
}
