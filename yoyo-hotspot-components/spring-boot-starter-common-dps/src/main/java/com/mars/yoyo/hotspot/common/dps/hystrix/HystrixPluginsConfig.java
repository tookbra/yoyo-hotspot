package com.mars.yoyo.hotspot.common.dps.hystrix;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author tookbra
 * @date 2018/5/30
 * @description
 */
@Configuration
public class HystrixPluginsConfig {

    @Bean
    public FilterRegistrationBean userInsertingMdcFilterRegistrationBean() {

        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new HystrixRequestContextEnablerFilter());
        return registration;
    }

    @Bean
    public FilterRegistrationBean setterInsertingMdcFilterRegistrationBean() {

        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new MicroContextHystrixRequestFilter());
        return registration;
    }

}
