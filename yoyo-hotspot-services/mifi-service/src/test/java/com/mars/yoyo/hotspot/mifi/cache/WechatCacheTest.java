package com.mars.yoyo.hotspot.mifi.cache;

import com.mars.yoyo.hotspot.mifi.BaseTest;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author tookbra
 * @date 2018/7/18
 * @description
 */
public class WechatCacheTest extends BaseTest {

    @Autowired
    WechatCache wechatCache;

    @Test
    public void putTokenTest() {
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = new WxMpOAuth2AccessToken();
        wxMpOAuth2AccessToken.setOpenId("1231");
        wechatCache.putToken("061gpFL02dnS401bneM02hJxL02gpFLZ", wxMpOAuth2AccessToken);
    }
}
