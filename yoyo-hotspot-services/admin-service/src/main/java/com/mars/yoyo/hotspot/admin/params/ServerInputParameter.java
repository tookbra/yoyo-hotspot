package com.mars.yoyo.hotspot.admin.params;

import lombok.Data;

/**
 * 设置服务器地址参数
 *
 * @author admin
 * @create 2018/6/20
 */
@Data
public class ServerInputParameter extends SessionParameter {

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
