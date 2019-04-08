package com.mars.yoyo.hotspot.mifi.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author tookbra
 * @date 2018/9/10
 * @description
 */
@Data
@Component
@ConfigurationProperties(prefix = "flow.server")
public class FlowProperties {

    private String baseUrl;

    private String authToken;

    private FlowProperties.RequestUrl requestUrl = new FlowProperties.RequestUrl();

    @Data
    public class RequestUrl {
        private String deviceUpdate;

        private String cdrDevice;

        private String cdrBrand;

        private String pkgOrder;

        private String pkgRecord;
    }
}
