package com.mars.yoyo.hotspot.device.packet.protocol;

import com.mars.yoyo.hotspot.device.packet.BoxPacketType;
import com.mars.yoyo.hotspot.device.packet.Message;
import lombok.Data;
import lombok.ToString;

/**
 * 弹出
 * @author tookbra
 * @date 2018/4/5
 * @description
 */
@Data
@ToString
public class PopUpMessage extends Message {
    private static final long serialVersionUID = 8078081494237645554L;
    /**
     * 槽位编号
     */
    private short slot;

    public PopUpMessage() {
        super(BoxPacketType.POPUP);
    }

    public enum MessageEnum {

        SLOT(1);

        private final static int bodyLength = SLOT.length;

        private int length;

        MessageEnum(int length) {
            this.length = length;
        }

        public static int getBodyLength() {
            return bodyLength;
        }
    }
}
