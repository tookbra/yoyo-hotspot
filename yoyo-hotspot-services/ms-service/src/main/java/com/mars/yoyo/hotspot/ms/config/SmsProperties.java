package com.mars.yoyo.hotspot.ms.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author tookbra
 * @date 2018/5/16
 * @description
 */
@Data
@ConfigurationProperties(prefix = "config.sms")
public class SmsProperties {

    /**
     * 开关
     */
    private Boolean enable;

    /**
     * 秒数间隔；60秒内只能发送一次
     */
    private Integer secondsInterval = 60;

    /**
     * 手机条数限制:单个号码一天内发送短信条数
     */
    private Integer topNumberLimit = 10;

    /**
     * IP限制：单个IP一天内发送短息条数
     */
    private Integer topIpLimit;

    /**
     * 手机号码绑定IP限制：单个手机号在1天内最多可绑定6个IP发送短信
     */
    private Integer topMobileIpLimit;
    /**
     * 单次发送手机号数量
     */
    private Integer onceSendNum = 100;

    private String name;

    private String pwd;

    private String url;
}
