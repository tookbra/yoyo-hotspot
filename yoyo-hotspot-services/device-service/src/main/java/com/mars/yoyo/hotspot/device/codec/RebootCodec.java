package com.mars.yoyo.hotspot.device.codec;

import com.mars.yoyo.hotspot.device.packet.Message;
import com.mars.yoyo.hotspot.device.packet.protocol.RebootMessage;
import com.mars.yoyo.hotspot.device.packet.protocol.RebootResMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 重启编解码器
 * @author tookbra
 * @date 2018/4/7
 * @description
 */
@Slf4j
public class RebootCodec extends MessageToMessageCodec<Message, RebootMessage> {
    @Override
    protected void encode(ChannelHandlerContext ctx, RebootMessage msg, List<Object> out) throws Exception {
        msg.setBody(new byte[0]);
        msg.getHeader().setBodyLength(msg.getBody().length);
        out.add(msg);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, Message msg, List<Object> out) throws Exception {
        RebootResMessage message = new RebootResMessage(msg.getHeader());
        out.add(message);
    }
}
