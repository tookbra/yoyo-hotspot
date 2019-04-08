package com.mars.yoyo.hotspot.ms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * 短信相关配置
 * @author tookbra
 * @date 2018/5/30
 * @description
 */
@Configuration
public class SmsConfiguration {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
