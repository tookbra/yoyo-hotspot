package com.mars.yoyo.hotspot.device;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author tookbra
 * @date 2018/4/4
 * @description
 */
@MapperScan(basePackages = "com.mars.yoyo.hotspot.device.dao")
@ComponentScan(basePackages = "com.mars.yoyo.hotspot.device")
@EnableFeignClients("com.mars.yoyo.hotspot.device.client")
@EnableAsync
@EnableCaching
@EnableScheduling
@EnableDiscoveryClient
@SpringBootApplication
public class DeviceApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .bannerMode(Banner.Mode.LOG)
                .sources(DeviceApplication.class)
                .run(args);
    }
}
