package com.mars.yoyo.hotspot.mifi;

import feign.Feign;
import feign.Param;
import feign.RequestLine;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.cloud.netflix.feign.support.SpringDecoder;
import org.springframework.cloud.netflix.feign.support.SpringEncoder;

/**
 * @author tookbra
 * @date 2018/5/30
 * @description
 */
public class FeignServiceTest {

    interface FeignClientInterface {
        @RequestLine("GET /s?wd={wd}&idx={idx}")
        String contributors(@Param("wd") String wd, @Param("idx") String idx);
    }

    public static void main(String... args) {
        //解码器
        SpringDecoder sd = new SpringDecoder(new ObjectFactory<HttpMessageConverters>() {
            @Override
            public HttpMessageConverters getObject() throws BeansException {
                return new HttpMessageConverters();
            }
        });
        //编码器
        SpringEncoder se = new SpringEncoder(new ObjectFactory<HttpMessageConverters>() {
            @Override
            public HttpMessageConverters getObject() throws BeansException {
                return new HttpMessageConverters();
            }
        });
        FeignClientInterface feignClient = Feign.builder()
                .encoder(se)
                .decoder(sd)
                .target(FeignClientInterface.class, "https://www.tookbra.com");

        // Fetch and print a list of the contributors to this library.
        String result = feignClient.contributors("aaa","ccc");
        System.out.println(result);
    }
}
