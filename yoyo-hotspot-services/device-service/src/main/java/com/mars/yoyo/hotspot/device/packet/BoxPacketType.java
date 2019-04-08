package com.mars.yoyo.hotspot.device.packet;

import com.mars.yoyo.hotspot.device.codec.*;
import io.netty.handler.codec.MessageToMessageCodec;

/**
 * @author tookbra
 * @date 2018/4/4
 * @description
 */
public enum BoxPacketType implements PacketType {
    CONNECT (0x60L, ConnectCodec.class),
    HEART(0x61L, HeartCodec.class),
    VERSION(0x62L, VersionCodec.class),
    SERVER(0x63L, ServerCodec.class),
    INVENTORY(0x64L, InventoryCodec.class),
    RENT(0x65L, RentCodec.class),
    RETURN(0x66L, ReturnCodec.class),
    REBOOT(0x67L, RebootCodec.class),
    UPGRADE(0x68L, UpgradeCodec.class),
    ICCID(0x69L, IccidCodec.class),
    POPUP(0x80L, PopUpCodec.class);


    private long commandId;
    private Class<? extends MessageToMessageCodec> codec;

    BoxPacketType(long commandId, Class<? extends MessageToMessageCodec> codec) {
        this.commandId = commandId;
        this.codec = codec;
    }

    @Override
    public long getCommandId() {
        return commandId;
    }

    @Override
    public MessageToMessageCodec getCodec() {
        try {
            return codec.newInstance();
        } catch (InstantiationException  | IllegalAccessException e) {
            return null;
        }
    }
}
