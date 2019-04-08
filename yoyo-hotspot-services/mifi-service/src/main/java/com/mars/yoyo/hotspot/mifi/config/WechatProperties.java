package com.mars.yoyo.hotspot.mifi.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author tookbra
 * @date 2018/4/17
 * @description
 */
@Data
@ConfigurationProperties(prefix = "wechat")
public class WechatProperties {

    /**
     * 设置微信三方平台的appid
     */
    private String appId;

    /**
     * 设置微信三方平台的app secret
     */
    private String secret;

    /**
     * 设置微信三方平台的token
     */
    private String token;

    /**
     * 设置微信三方平台的EncodingAESKey
     */
    private String aesKey;

    private String url;

    private Pay pay = new Pay();

    @Data
    public static class Pay {
        /**
         * 微信支付商户号
         */
        private String mchId;

        /**
         * 微信支付商户密钥
         */
        private String mchKey;

        /**
         * 服务商模式下的子商户公众账号ID，普通模式请不要配置，请在配置文件中将对应项删除
         */
        private String subAppId;

        /**
         * 服务商模式下的子商户号，普通模式请不要配置，最好是请在配置文件中将对应项删除
         */
        private String subMchId;

        /**
         * apiclient_cert.p12文件的绝对路径，或者如果放在项目中，请以classpath:开头指定
         */
        private String keyPath;
        /**
         * 回调地址
         */
        private String notifyUrl;
        /**
         * 沙箱环境
         */
        private boolean useSandboxEnv = false;
    }
}
