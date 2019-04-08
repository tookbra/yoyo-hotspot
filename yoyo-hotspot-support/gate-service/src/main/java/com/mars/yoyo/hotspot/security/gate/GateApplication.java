package com.mars.yoyo.hotspot.security.gate;

import org.springframework.boot.Banner;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author tookbra
 * @date 2018/5/25
 * @description
 */
@EnableFeignClients(basePackages= {"com.mars.yoyo.hotspot.security.gate.client"})
@EnableZuulProxy
@SpringCloudApplication
@ComponentScan("com.mars.yoyo.hotspot.security.gate")
public class GateApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .bannerMode(Banner.Mode.LOG)
                .sources(GateApplication.class)
                .run(args);
    }
}
