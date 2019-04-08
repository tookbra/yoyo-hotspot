package com.mars.yoyo.hotspot.device.packet.protocol;

import com.mars.yoyo.hotspot.device.packet.BoxPacketType;
import com.mars.yoyo.hotspot.device.packet.Message;
import lombok.Data;

/**
 * 升级
 * @author tookbra
 * @date 2018/4/5
 * @description
 */
@Data
public class UpgradeMessage extends Message {

    private static final long serialVersionUID = 6360739959566559416L;
    /**
     * FTP 服务器地址长度
     */
    private short ftpAddressLen;
    /**
     * FTP 服务器地址
     */
    private String ftpAddress;
    /**
     * FTP 服务器端口长度
     */
    private short ftpPortLen;
    /**
     * FTP 服务器端口
     */
    private String ftpPort;
    /**
     * 文件名长度
     */
    private long fileNameLen;
    /**
     * 文件名
     */
    private String fileName;
    /**
     * 用户名长度
     */
    private short usernameLen;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码长度
     */
    private short passwordLen;
    /**
     * 密码
     */
    private String password;

    public UpgradeMessage() {
        super(BoxPacketType.UPGRADE);
    }

    public enum MessageEnum {

        FTP_ADDRESS_LEN(2),
        FTP_PORT_LEN(2),
        FILE_NAME_LEN(2),
        USERNAME_LEN(2),
        PASSWORD_LEN(2);

        private final static int bodyLength = FTP_ADDRESS_LEN.length + FTP_PORT_LEN.length + FILE_NAME_LEN.length
                + USERNAME_LEN.length + PASSWORD_LEN.length;

        private int length;

        MessageEnum(int length) {
            this.length = length;
        }

        public static int getBodyLength() {
            return bodyLength;
        }
    }
}
