package com.mars.yoyo.hotspot.cmp.config.rest;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author tookbra
 * @date 2018/5/12
 * @description
 */
@Data
@Component
@ConfigurationProperties(prefix = "cmp.server")
public class CmpProperties {

    private String baseUrl;

    private int port;

    private String version;

    private String userId;

    private String apiKey;

    private HttpConfig httpConfig = new HttpConfig();

    private RequestUrl requestUrl = new RequestUrl();

    private Proxy proxy = new Proxy();

    @Data
    public class Proxy {
        /**
         * 是否开启 代理
         */
        private boolean enable = false;
        /**
         * 代理地址
         */
        private String hostName = "127.0.0.1";
        /**
         * 代理端口
         */
        private int port = 8000;
    }

    @Data
    public class HttpConfig {
        /**
         * TTL
         */
        private int timeToLive = 30;
        /**
         * 最大连接
         */
        private int maxTotal = 200;
        /**
         * 每个route最大连接，每个url最大连接数
         */
        private int maxPerRoute = 200;
        /**
         * 是否启用重试
         */
        private boolean retryEnabled = false;
        /**
         * 重试次数
         */
        private int retryCount = 3;
        /**
         * 连接超时
         */
        private int connectTimeout = 5000;
        /**
         * 读取超时
         */
        private int readTimeout = 5000;
        /**
         * 连接池获取超时
         */
        private int connectionRequestTimeout = 200;
    }

    @Data
    public class RequestUrl {
        private String sendOrder;

        private String amount;

        private String chaxun;

        private String sendMsg;

        private String msgStatus;

        private String start;

        private String stop;

    }

}
