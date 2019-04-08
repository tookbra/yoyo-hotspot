package com.mars.yoyo.hotspot.device.cache;

import com.mars.yoyo.hotspot.device.BaseTest;
import com.mars.yoyo.hotspot.device.session.MifiSession;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author tookbra
 * @date 2018/7/2
 * @description
 */
public class SessionCacheTest extends BaseTest {

    @Autowired
    SessionCache sessionCache;

    @Test
    public void putSessionTest() {
        MifiSession mifiSession = new MifiSession();
        mifiSession.setSessionId("222");
        mifiSession.setDeviceId("333");
        sessionCache.putSession(mifiSession);
    }
}
