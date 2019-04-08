package com.mars.yoyo.hotspot.device.session;

import com.mars.yoyo.hotspot.device.cache.SessionCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author tookbra
 * @date 2018/4/16
 * @description
 */
@Component
public class DefaultSessionManager implements SessionManager {


    @Autowired
    SessionCache sessionCache;

    @Override
    public void remove(MifiSession session) {

    }

    @Override
    public void save(MifiSession session) {
        if(null != session) {
            sessionCache.putSession(session);
        }
    }

    @Override
    public MifiSession get(String boxId) {
        return sessionCache.getSession(boxId);
    }
}
