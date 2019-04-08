package com.mars.yoyo.hotspot.device.session;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author tookbra
 * @date 2018/4/16
 * @description
 */
@Data
@ToString
public class MifiSession implements Serializable {

    /**
     * 设备id
     */
    private String deviceId;
    private String sessionId;

    public MifiSession() {}

    public MifiSession(String sessionId) {
        this.sessionId = sessionId;
    }

}
