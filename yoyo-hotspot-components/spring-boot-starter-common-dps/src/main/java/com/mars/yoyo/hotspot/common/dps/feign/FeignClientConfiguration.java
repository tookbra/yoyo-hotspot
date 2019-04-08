package com.mars.yoyo.hotspot.common.dps.feign;

import org.springframework.cloud.netflix.feign.FeignClientsConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * feign配置
 * @author tookbra
 * @date 2018/5/29
 * @description
 */
@Configuration
@Import(FeignClientsConfiguration.class)
public class FeignClientConfiguration {
}
