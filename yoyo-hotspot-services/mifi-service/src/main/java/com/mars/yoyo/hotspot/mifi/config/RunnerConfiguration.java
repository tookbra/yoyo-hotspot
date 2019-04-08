package com.mars.yoyo.hotspot.mifi.config;

import com.mars.yoyo.hotspot.mifi.listener.RentListener;
import com.mars.yoyo.hotspot.mifi.runner.CacheInitRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author tookbra
 * @date 2018/6/3
 * @description
 */
@Configuration
public class RunnerConfiguration {

    @Bean
    public CacheInitRunner cacheInitRunner() {
        return new CacheInitRunner();
    }

    @Bean
    public RentListener rentListener() {
        return new RentListener();
    }
}
