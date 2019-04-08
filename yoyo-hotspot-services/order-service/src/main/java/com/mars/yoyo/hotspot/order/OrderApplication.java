package com.mars.yoyo.hotspot.order;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author tookbra
 * @date 2018/5/17
 * @description
 */
@SpringBootApplication
public class OrderApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .bannerMode(Banner.Mode.LOG)
                .sources(OrderApplication.class)
                .run(args);
    }
}
