package com.mars.yoyo.hotspot.mifi.config;

import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author tookbra
 * @date 2018/4/18
 * @description
 */
@Configuration
@ConditionalOnClass(WxPayService.class)
//@ConditionalOnProperty(name="wechat.pay")
@EnableConfigurationProperties({WechatProperties.class})
public class WechaPayConfiguration {

    @Autowired
    private WechatProperties wechatProperties;

//    public WechaPayConfiguration(WechatProperties wechatProperties) {
//        wechatProperties = this.wechatProperties;
//    }

    @Bean
    @ConditionalOnMissingBean
    public WxPayConfig payConfig() {
        WxPayConfig payConfig = new WxPayConfig();
        payConfig.setAppId(wechatProperties.getAppId());
        payConfig.setMchId(wechatProperties.getPay().getMchId());
        payConfig.setMchKey(wechatProperties.getPay().getMchKey());
//        payConfig.setSubAppId(StringUtils.trimToNull(wechatProperties.getPay().getSubAppId()));
//        payConfig.setSubMchId(StringUtils.trimToNull(wechatProperties.getPay().getSubMchId()));
        payConfig.setUseSandboxEnv(wechatProperties.getPay().isUseSandboxEnv());
//        payConfig.setKeyPath(wechatProperties.getPay().getKeyPath());

        return payConfig;
    }

    @Bean
    @ConditionalOnMissingBean
    public WxPayService wxPayService(WxPayConfig payConfig) {
        WxPayService wxPayService = new WxPayServiceImpl();
        wxPayService.setConfig(payConfig);
        return wxPayService;
    }
}
