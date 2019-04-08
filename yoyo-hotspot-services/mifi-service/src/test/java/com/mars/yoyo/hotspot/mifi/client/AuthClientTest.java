package com.mars.yoyo.hotspot.mifi.client;

import com.mars.yoyo.hotspot.mifi.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author tookbra
 * @date 2018/8/17
 * @description
 */
public class AuthClientTest extends BaseTest {

    @Autowired
    AuthClient authClient;

    @Test
    public void wxLogin() throws Exception {
        System.out.println(123);
        Thread.sleep(10000);
        authClient.wxLogin("12", 2);
        System.in.read();
    }
}
