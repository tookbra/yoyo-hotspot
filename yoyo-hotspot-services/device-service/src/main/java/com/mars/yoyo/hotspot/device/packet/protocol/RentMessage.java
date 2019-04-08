package com.mars.yoyo.hotspot.device.packet.protocol;

import com.mars.yoyo.hotspot.device.packet.BoxPacketType;
import com.mars.yoyo.hotspot.device.packet.Header;
import com.mars.yoyo.hotspot.device.packet.Message;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 租借
 * @author tookbra
 * @date 2018/4/4
 * @description
 */
@Data
public class RentMessage extends Message {
    private static final long serialVersionUID = -1867341693986362236L;

    /**
     * 槽位编号
     */
    private short slot;

    public RentMessage() {
        super(BoxPacketType.RENT);
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
