package com.mars.yoyo.hotspot.common.dps.secutiry;

import com.mars.yoyo.hotspot.common.dps.interceptor.DefaultSecurityHandlerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * @author tookbra
 * @date 2018/6/4
 * @description
 */
@Configuration
public class CoreWebMvcConfigurationSupport  extends WebMvcConfigurerAdapter {

    @Bean
    public DefaultSecurityHandlerInterceptor defaultSecurityHandlerInterceptor() {
        return new DefaultSecurityHandlerInterceptor();
    }

    @Bean
    public UserEnvArgumentResolver coreHandlerMethodArgumentResolver() {
        return new UserEnvArgumentResolver(UserEnv.class);
    }

    /**
     * 拦截器添加
     * @param registry 拦截器注册
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(defaultSecurityHandlerInterceptor());
    }

    /**
     * controller 参数解析
     *
     * @param argumentResolvers
     *            参数解析列表
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(coreHandlerMethodArgumentResolver());
    }
}
