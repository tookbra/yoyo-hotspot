package com.mars.yoyo.hotspot.cmp;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;

/**
 * @author tookbra
 * @date 2018/5/7
 * @description
 */
@Import({RestTemplate.class})
//@ComponentScan(basePackages = "com.mars.yoyo.hotspot.cmp")
@EnableDiscoveryClient
@SpringBootApplication
public class CmpApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .bannerMode(Banner.Mode.LOG)
                .sources(CmpApplication.class)
                .run(args);
    }
}
