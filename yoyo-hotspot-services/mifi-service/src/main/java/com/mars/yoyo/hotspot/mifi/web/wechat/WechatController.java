package com.mars.yoyo.hotspot.mifi.web.wechat;

import com.mars.yoyo.hotspot.mifi.config.WechatProperties;
import com.mars.yoyo.hotspot.result.RestResult;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tookbra
 * @date 2018/4/18
 * @description
 */
@Slf4j
@RestController
@RequestMapping("/wechat")
public class WechatController {

    @Autowired
    WechatProperties wechatProperties;

    @Autowired
    WxMpService wxService;

    /**
     *
     * @return
     */
    @GetMapping("/config")
    public RestResult getConfig() throws WxErrorException {
        WxJsapiSignature wxJsapiSignature =  wxService.createJsapiSignature(wechatProperties.getUrl());
        return RestResult.success(wxJsapiSignature);
    }


}
