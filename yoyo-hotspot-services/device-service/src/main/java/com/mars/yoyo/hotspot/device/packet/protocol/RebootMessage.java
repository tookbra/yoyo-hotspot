package com.mars.yoyo.hotspot.device.packet.protocol;

import com.mars.yoyo.hotspot.device.packet.BoxPacketType;
import com.mars.yoyo.hotspot.device.packet.Message;
import lombok.Data;
import lombok.ToString;

/**
 * 重启
 * @author tookbra
 * @date 2018/4/5
 * @description
 */
@Data
@ToString
public class RebootMessage extends Message {

    public RebootMessage() {
        super(BoxPacketType.REBOOT);
    }
}
