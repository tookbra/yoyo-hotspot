package com.mars.yoyo.hotspot.device.dto.input;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author tookbra
 * @date 2018/6/12
 * @description
 */
@Data
@ToString
public class UpgradeInput implements Serializable {

    private static final long serialVersionUID = -8645231083974819108L;

    /**
     * 设备id
     */
    private String boxId;
    /**
     * FTP 服务器地址
     */
    private String ftpAddress;
    /**
     * FTP 服务器端口
     */
    private String ftpPort;
    /**
     * 文件名
     */
    private String fileName;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
}
