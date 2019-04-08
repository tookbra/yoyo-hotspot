package com.mars.yoyo.hotspot.mifi.service;

import com.mars.yoyo.hotspot.mifi.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author tookbra
 * @date 2018/5/15
 * @description
 */
public class ProductServiceTest extends BaseTest {

    @Autowired
    ProductService productService;

    @Test
    public void getCurrentProductTest() {
        productService.getCurrentProduct();
    }
}
