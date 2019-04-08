package com.mars.yoyo.hotspot.admin.params;

import lombok.Data;
import lombok.ToString;

/**
 * 远程更新接口参数
 *
 * @author admin
 * @create 2018/6/20
 */
@Data
@ToString
public class UpgradeInputParameter extends SessionParameter {

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
