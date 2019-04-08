package com.mars.yoyo.hotspot.order.config;

import com.paypal.base.rest.APIContext;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author tookbra
 * @date 2018/5/17
 * @description
 */
@Configuration
@EnableConfigurationProperties({PaypalProperties.class})
public class PaypalConfiguration {

    private PaypalProperties paypalProperties;

    public PaypalConfiguration(PaypalProperties paypalProperties) {
        this.paypalProperties = paypalProperties;
    }

    @Bean
    public APIContext apiContext() {
        APIContext apiContext = new APIContext(paypalProperties.getId(), paypalProperties.getSecret(), paypalProperties.getMode());
        return apiContext;
    }
}
