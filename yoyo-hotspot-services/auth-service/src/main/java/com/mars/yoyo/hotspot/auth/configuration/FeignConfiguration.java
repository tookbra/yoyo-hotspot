package com.mars.yoyo.hotspot.auth.configuration;

import com.mars.yoyo.hotspot.auth.client.fallback.UserClientFallback;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author tookbra
 * @date 2018/5/26
 * @description
 */
@Configuration
public class FeignConfiguration {

    @Bean
    public UserClientFallback userClientFallback() {
        return new UserClientFallback();
    }
}
