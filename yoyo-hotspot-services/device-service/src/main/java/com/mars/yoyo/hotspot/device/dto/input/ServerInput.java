package com.mars.yoyo.hotspot.device.dto.input;

import lombok.Data;

import java.io.Serializable;

/**
 * @author tookbra
 * @date 2018/6/12
 * @description
 */
@Data
public class ServerInput implements Serializable {

    /**
     * 设备id
     */
    private String boxId;
    /**
     * 服务器地址
     */
    private String address;
    /**
     * 服务器端口
     */
    private String port;
    /**
     * 心跳间隔(1~255 有效)
     */
    private byte heartbeat;
}
