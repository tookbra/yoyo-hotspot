package com.mars.yoyo.hotspot.mifi.config;

import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author tookbra
 * @date 2018/4/17
 * @description
 */
@Configuration
@EnableConfigurationProperties({WechatProperties.class})
public class WechatMpConfiguration {

    private WechatProperties wechatProperties;

    public WechatMpConfiguration(WechatProperties wechatProperties) {
        this.wechatProperties = wechatProperties;
    }

    @Bean
    @ConditionalOnMissingBean()
    public WxMpConfigStorage wxMpConfigStorage() {
        WxMpInMemoryConfigStorage config = new WxMpInMemoryConfigStorage();

        config.setAppId(wechatProperties.getAppId());
        config.setSecret(wechatProperties.getSecret());
        config.setToken(wechatProperties.getToken());
        config.setAesKey(wechatProperties.getAesKey());
        return config;
    }

    @Bean
    @ConditionalOnMissingBean
    public WxMpService wxMpService(WxMpConfigStorage wxMpConfigStorage) {
        WxMpService wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(wxMpConfigStorage);
        return wxMpService;
    }

    @Bean
    public WxMpMessageRouter router(WxMpService wxMpService) {
        final WxMpMessageRouter newRouter = new WxMpMessageRouter(wxMpService);
        return newRouter;
    }
}
