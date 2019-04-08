package com.mars.yoyo.hotspot.device.packet.protocol;

import com.mars.yoyo.hotspot.device.packet.BoxPacketType;
import com.mars.yoyo.hotspot.device.packet.Message;
import lombok.Data;

/**
 * 登陆包
 * @author tookbra
 * @date 2018/4/4
 * @description
 */
@Data
public class ConnectResMessage extends Message {
    private static final long serialVersionUID = -2629675097704039164L;
    /**
     * 登陆结果 0 失败 1成功 2失败，时间错误
     */
    private short status;

    public ConnectResMessage() {
        super(BoxPacketType.CONNECT);
    }

    public enum MessageEnum {

        STATUS(1);

        private final static int bodyLength = STATUS.length;

        private int length;

        MessageEnum(int length) {
            this.length = length;
        }

        public static int getBodyLength() {
            return bodyLength;
        }
    }
}
