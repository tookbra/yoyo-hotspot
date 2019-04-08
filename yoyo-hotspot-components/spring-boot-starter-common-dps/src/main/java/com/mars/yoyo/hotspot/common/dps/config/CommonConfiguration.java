package com.mars.yoyo.hotspot.common.dps.config;

import com.mars.yoyo.hotspot.common.dps.advice.RestAdvice;
import com.mars.yoyo.hotspot.common.dps.aspect.FeignExceptionHandlerAspect;
import com.mars.yoyo.hotspot.common.dps.aspect.HibernateValidatorAspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 公共配置
 * @author tookbra
 * @date 2018/5/29
 * @description
 */
@Configuration
public class CommonConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public HibernateValidatorAspect hibernateValidatorAspect() {
        final int order = Byte.MAX_VALUE + 2;
        return new HibernateValidatorAspect(order);
    }

    @Bean
    @ConditionalOnMissingBean
    public FeignExceptionHandlerAspect feignExceptionHandlerAspect() {
        final int order = Byte.MAX_VALUE + 3;
        return new FeignExceptionHandlerAspect();
    }

    @Bean
    public RestAdvice restAdvice() {
        return new RestAdvice();
    }
}
