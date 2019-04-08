package com.mars.yoyo.hotspot.device.packet.protocol;

import com.mars.yoyo.hotspot.device.packet.BoxPacketType;
import com.mars.yoyo.hotspot.device.packet.Message;
import lombok.Data;

/**
 * 心跳包
 * @author tookbra
 * @date 2018/4/4
 * @description
 */
@Data
public class HeartResMessage extends Message {
    private static final long serialVersionUID = -3515080981064337738L;

    public HeartResMessage() {
        super(BoxPacketType.HEART);
    }
}
