package com.mars.yoyo.hotspot.swagger;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author tookbra
 * @date 2018/4/4
 * @description
 */
@Data
@ConfigurationProperties(prefix = "swagger.ui")
public class SwaggerProperties {

    /**
     * 是否开启swagger
     */
    private boolean enabled = false;
    /**
     * 包路径
     */
    private String basePackage;
    /**
     * 标题
     */
    private String title;
    /**
     * 描述
     */
    private String description;
    /**
     *
     */
    private String termUrl;
    /**
     * 版本
     */
    private String version;

    private String name;

    private String url;
    /**
     * email
     */
    private String email;
}
