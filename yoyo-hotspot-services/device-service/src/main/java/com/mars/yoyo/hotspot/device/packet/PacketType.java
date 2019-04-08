package com.mars.yoyo.hotspot.device.packet;

import io.netty.handler.codec.MessageToMessageCodec;

/**
 * @author tookbra
 * @date 2018/4/4
 * @description
 */
public interface PacketType {

    /**
     * 获取命令
     * @return
     */
    long getCommandId();

    /**
     * 获取对应的编解码器
     * @return
     */
    MessageToMessageCodec getCodec();
}
