package com.mars.yoyo.hotspot.config;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * @author tookbra
 * @date 2018/6/12
 * @description
 */
@EnableConfigServer
@SpringBootApplication
@RefreshScope
public class ConfigApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .bannerMode(Banner.Mode.LOG)
                .sources(ConfigApplication.class)
                .run(args);
    }
}
