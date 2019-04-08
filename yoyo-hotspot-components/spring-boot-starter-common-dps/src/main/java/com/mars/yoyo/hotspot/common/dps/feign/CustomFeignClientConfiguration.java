package com.mars.yoyo.hotspot.common.dps.feign;

import com.mars.yoyo.hotspot.common.dps.util.RestStatusUtil;
import com.mars.yoyo.hotspot.exception.MicroException;
import com.netflix.hystrix.exception.HystrixBadRequestException;
import feign.Logger;
import feign.Util;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.cloud.netflix.feign.support.SpringDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

import java.io.IOException;

import static feign.FeignException.errorStatus;


/**
 * @author tookbra
 * @date 2018/5/29
 * @description
 */
@Configuration
@Slf4j
public class CustomFeignClientConfiguration {

    @Autowired
    private ObjectFactory<HttpMessageConverters> messageConverters;

    @Bean
    public ErrorDecoder buildErrorDecoder() {
        return (methodKey, response) -> {
            log.error("feign invoke error. method={}, reason={}", methodKey, response);
            try {
                if (response.body() != null) {
                    MicroException exception = RestStatusUtil.decode(HttpStatus.valueOf(response.status()),
                            Util.toString(response.body().asReader()));
                    return new HystrixBadRequestException(exception.getMessage(), exception);
                }
            } catch (IOException e) {

            }
            return errorStatus(methodKey, response);
        };
    }

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean
    public Encoder buildEncoder() {
        return new FeignEncoder(messageConverters);
    }

    @Bean
    public Decoder feignDecoder() {
        return new FeignDecoder(new SpringDecoder(this.messageConverters));
    }
}
