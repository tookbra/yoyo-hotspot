package com.mars.yoyo.hotspot.admin.service;

import com.mars.yoyo.hotspot.admin.BaseTest;
import com.mars.yoyo.hotspot.admin.config.RedisHelper;
import org.junit.Test;

/**
 * redis
 *
 * @author admin
 * @create 2018/8/30
 */
public class RedisTest extends BaseTest {

    @Test
    public void testRedis() {
        RedisHelper.set("test", "test", 10000L);
        System.err.println(RedisHelper.get("test").toString() + "=======================================");
    }


}
