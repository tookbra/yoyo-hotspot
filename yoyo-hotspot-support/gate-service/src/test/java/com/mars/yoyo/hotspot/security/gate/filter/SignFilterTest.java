package com.mars.yoyo.hotspot.security.gate.filter;

import com.mars.yoyo.hotspot.security.gate.BaseTest;
import com.mars.yoyo.hotspot.security.gate.constant.RedisConstants;
import org.junit.Test;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author tookbra
 * @date 2018/5/26
 * @description
 */
public class SignFilterTest extends BaseTest {

    @Autowired
    RedissonClient redissonClient;

    @Test
    public void userNonceTest() throws IOException {
        String userNonceKey = RedisConstants.getNonceKey(1, "123");
        RBucket rBucket = redissonClient.getBucket(userNonceKey);
        rBucket.set("123", 60, TimeUnit.SECONDS);
        System.in.read();
    }
}
