package com.mars.yoyo.hotspot.device.packet.protocol;

import com.mars.yoyo.hotspot.device.packet.BoxPacketType;
import com.mars.yoyo.hotspot.device.packet.Message;
import lombok.Data;

/**
 * 库存
 * @author tookbra
 * @date 2018/4/4
 * @description
 */
@Data
public class InventoryMessage extends Message {
    private static final long serialVersionUID = -9207646341497521180L;

    public InventoryMessage() {
        super(BoxPacketType.INVENTORY);
    }
}
