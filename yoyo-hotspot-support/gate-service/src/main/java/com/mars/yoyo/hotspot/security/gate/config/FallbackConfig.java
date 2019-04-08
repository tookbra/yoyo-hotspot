package com.mars.yoyo.hotspot.security.gate.config;

import com.mars.yoyo.hotspot.security.gate.client.fallback.AuthClientFallback;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author tookbra
 * @date 2018/5/26
 * @description
 */
@Configuration
public class FallbackConfig {

    @Bean
    public AuthClientFallback authClientFallback() {
        return new AuthClientFallback();
    }
}
