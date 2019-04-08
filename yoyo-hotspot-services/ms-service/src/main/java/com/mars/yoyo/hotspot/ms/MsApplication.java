package com.mars.yoyo.hotspot.ms;

import com.mars.yoyo.hotspot.ms.cache.SmsCaptchaCache;
import com.mars.yoyo.hotspot.ms.cache.SmsTemplateCache;
import com.mars.yoyo.hotspot.ms.config.SmsProperties;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author tookbra
 * @date 2018/5/15
 * @description
 */
@EnableAsync
@EnableCaching
@EnableDiscoveryClient
@SpringBootApplication
@EnableConfigurationProperties(SmsProperties.class)
@Import({SmsTemplateCache.class, SmsCaptchaCache.class})
@MapperScan(basePackages = "com.mars.yoyo.hotspot.ms.dao")
@EnableFeignClients(basePackages= {"com.mars.yoyo.hotspot.ms.client"})
@ComponentScan(basePackages = {"com.mars.yoyo.hotspot.ms"})
public class MsApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .bannerMode(Banner.Mode.LOG)
                .sources(MsApplication.class)
                .run(args);
    }
}
