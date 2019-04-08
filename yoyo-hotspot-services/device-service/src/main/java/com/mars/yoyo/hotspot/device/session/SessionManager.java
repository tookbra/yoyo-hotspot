package com.mars.yoyo.hotspot.device.session;


/**
 * @author tookbra
 * @date 2018/4/16
 * @description
 */
public interface SessionManager {

    /**
     * 移除
     * @param session
     */
    void remove(MifiSession session);

    /**
     * 保存
     * @param session
     */
    void save(MifiSession session);

    /**
     * 获取
     * @param boxId
     * @return
     */
    MifiSession get(String boxId);
}
