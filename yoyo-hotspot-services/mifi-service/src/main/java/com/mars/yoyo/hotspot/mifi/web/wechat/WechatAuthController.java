package com.mars.yoyo.hotspot.mifi.web.wechat;

import com.mars.yoyo.hotspot.mifi.cache.WechatCache;
import com.mars.yoyo.hotspot.mifi.config.WechatProperties;
import com.mars.yoyo.hotspot.mifi.dto.AppWxLoginDto;
import com.mars.yoyo.hotspot.mifi.service.UserAuthService;
import com.mars.yoyo.hotspot.mifi.vo.WxTokenVo;
import com.mars.yoyo.hotspot.result.RestResult;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author tookbra
 * @date 2018/5/8
 * @description
 */
@Slf4j
@RestController
@RequestMapping("/wechat/auth")
public class WechatAuthController {

    @Autowired
    WxMpService wxService;

    @Autowired
    WechatCache wechatCache;

    @Autowired
    WechatProperties wechatProperties;

    @Autowired
    UserAuthService userAuthService;

    /**
     *
     * @return
     */
    @GetMapping()
    public RestResult auth(String path) {
        String authUrl = wxService.oauth2buildAuthorizationUrl(wechatProperties.getUrl() + '#' + path,
                WxConsts.OAuth2Scope.SNSAPI_USERINFO, null);
        return RestResult.success(authUrl);
    }

    /**
     *
     * @return
     */
    @GetMapping("/token")
    public RestResult<WxTokenVo> authCode(@RequestParam String code) throws WxErrorException {
        WxTokenVo wxTokenVo = new WxTokenVo();
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = wxService.oauth2getAccessToken(code);
        wxMpOAuth2AccessToken = wxService.oauth2refreshAccessToken(wxMpOAuth2AccessToken.getRefreshToken());
        WxMpUser wxMpUser = wxService.oauth2getUserInfo(wxMpOAuth2AccessToken, null);
        wechatCache.putToken(code, wxMpOAuth2AccessToken);
        wechatCache.putUser(code, wxMpUser);
        wxTokenVo.setWxToken(wxMpUser.getUnionId());
        String token = userAuthService.wxLogin(wxMpUser.getUnionId());
        if(StringUtils.isNotBlank(token)) {
            wxTokenVo.setToken(token);
        }
        return RestResult.success(wxTokenVo);
    }

    @PostMapping("/validate")
    public RestResult<WxTokenVo> authCode(@RequestParam String openId, @RequestParam String token) {
        WxTokenVo wxTokenVo = new WxTokenVo();
        String newToken = userAuthService.validate(openId, token);
        wxTokenVo.setToken(newToken);
        return RestResult.success(wxTokenVo);
    }

    /**
     * app微信登陆
     * @param unionId
     * @return
     */
    @PostMapping("/login")
    public RestResult<AppWxLoginDto> login(@RequestParam String unionId) {
        AppWxLoginDto appWxLoginDto = userAuthService.wxLoginByUnionId(unionId);
        return RestResult.success(appWxLoginDto);
    }


}
