package com.mars.yoyo.hotspot.device.codec;

import com.mars.yoyo.hotspot.device.packet.Message;
import com.mars.yoyo.hotspot.device.packet.protocol.HeartMessage;
import com.mars.yoyo.hotspot.device.packet.protocol.HeartResMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;

import java.util.List;

/**
 * 心跳编解码器
 * @author tookbra
 * @date 2018/4/6
 * @description
 */
public class HeartCodec extends MessageToMessageCodec<Message, HeartResMessage> {
    @Override
    protected void encode(ChannelHandlerContext ctx, HeartResMessage msg, List<Object> out) throws Exception {
        msg.setBody(new byte[0]);
        msg.getHeader().setBodyLength(msg.getBody().length);
        out.add(msg);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, Message msg, List<Object> out) throws Exception {
        HeartMessage message = new HeartMessage(msg.getHeader());
        out.add(message);
    }
}
