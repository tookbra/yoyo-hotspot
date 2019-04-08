package com.mars.yoyo.hotspot.mifi.config;

import com.mars.yoyo.hotspot.mifi.client.fallback.SmsClientFallback;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author tookbra
 * @date 2018/5/29
 * @description
 */
@Configuration
public class FeignFallbackConfig {

    @Bean
    public SmsClientFallback smsClientFallback() {
        return new SmsClientFallback();
    }
}
