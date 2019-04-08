package com.mars.yoyo.hotspot.order.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author tookbra
 * @date 2018/5/17
 * @description
 */
@Data
@ConfigurationProperties(prefix = "paypal.client")
public class PaypalProperties {

    private String id;
    private String secret;
    private String mode;
}
