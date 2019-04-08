package com.mars.yoyo.hotspot.device.handler;

import com.mars.yoyo.hotspot.device.packet.Message;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author tookbra
 * @date 2018/4/5
 * @description
 */
public interface MessageHandler {

    void call(ChannelHandlerContext ctx, Message message);
}
