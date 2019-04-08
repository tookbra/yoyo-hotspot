package com.mars.yoyo.hotspot.device.packet.protocol;

import com.mars.yoyo.hotspot.device.packet.BoxPacketType;
import com.mars.yoyo.hotspot.device.packet.Message;
import lombok.Data;

/**
 * ICCID
 * @author tookbra
 * @date 2018/4/5
 * @description
 */
@Data
public class IccidMessage extends Message {
    private static final long serialVersionUID = -4292363844941957675L;

    public IccidMessage() {
        super(BoxPacketType.ICCID);
    }
}
