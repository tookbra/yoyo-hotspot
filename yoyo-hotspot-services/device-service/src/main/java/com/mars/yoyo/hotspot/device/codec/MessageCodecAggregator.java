package com.mars.yoyo.hotspot.device.codec;

import com.mars.yoyo.hotspot.device.packet.BoxPacketType;
import com.mars.yoyo.hotspot.device.packet.Message;
import com.mars.yoyo.hotspot.device.packet.PacketType;
import io.netty.channel.*;
import io.netty.handler.codec.MessageToMessageCodec;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 编解码适配
 * @author tookbra
 * @date 2018/4/4
 * @description
 */
@ChannelHandler.Sharable
public class MessageCodecAggregator extends ChannelDuplexHandler {

    public static final MessageCodecAggregator INSTANCE = new MessageCodecAggregator();

    private ConcurrentHashMap<Long, MessageToMessageCodec> handlerMap = new ConcurrentHashMap<Long, MessageToMessageCodec>();

    public MessageCodecAggregator() {
        for (PacketType packetType : BoxPacketType.values()) {
            handlerMap.put(packetType.getCommandId(), packetType.getCodec());
        }
    }


    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        long commandId = ((Message) msg).getHeader().getCommandId();
        MessageToMessageCodec codec = handlerMap.get(commandId);
        codec.write(ctx, msg, promise);

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        long commandId = ((Message) msg).getHeader().getCommandId();
        MessageToMessageCodec codec = handlerMap.get(commandId);
        codec.channelRead(ctx, msg);
    }
}
