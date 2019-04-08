package com.mars.yoyo.hotspot.device.packet.protocol;

import com.mars.yoyo.hotspot.device.packet.BoxPacketType;
import com.mars.yoyo.hotspot.device.packet.Message;
import lombok.Data;

/**
 * @author tookbra
 * @date 2018/4/4
 * @description
 */
@Data
public class ServerMessage extends Message {
    private static final long serialVersionUID = -2931660416308582931L;
    /**
     * 服务器地址长度
     */
    private short addressLen;
    /**
     * 服务器地址
     */
    private String address;
    /**
     * 服务器端口长度
     */
    private short portLen;
    /**
     * 服务器端口
     */
    private String port;
    /**
     * 心跳间隔(1~255 有效)
     */
    private byte heartbeat;

    public ServerMessage() {
        super(BoxPacketType.SERVER);
    }

    public enum MessageEnum {

        ADDRESS_LEN(2),
        PORT_LEN(2),
        HEARTBEAT(1);

        private final static int bodyLength = ADDRESS_LEN.length + PORT_LEN.length + HEARTBEAT.length;

        private int length;

        MessageEnum(int length) {
            this.length = length;
        }

        public static int getBodyLength() {
            return bodyLength;
        }
    }
}
