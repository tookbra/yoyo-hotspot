package com.mars.yoyo.hotspot.mifi.service;

import com.mars.yoyo.hotspot.mifi.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author tookbra
 * @date 2018/6/22
 * @description
 */
public class OrderServiceTest extends BaseTest {

    @Autowired
    OrderService orderService;

    @Test
    public void notifyTest() {
        orderService.notifyPaypal("PAY-2KF64262UJ573202HLMWLWAA");

    }
}
