package com.mars.yoyo.hotspot.device.codec;

import com.mars.yoyo.hotspot.device.packet.Message;
import com.mars.yoyo.hotspot.device.packet.protocol.ServerMessage;
import com.mars.yoyo.hotspot.device.packet.protocol.ServerResMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 服务器地址编解码器
 * @author tookbra
 * @date 2018/4/6
 * @description
 */
@Slf4j
public class ServerCodec extends MessageToMessageCodec<Message, ServerMessage> {
    @Override
    protected void encode(ChannelHandlerContext ctx, ServerMessage msg, List<Object> out) throws Exception {
        byte [] address = msg.getAddress().getBytes(CharsetUtil.UTF_8);
        byte [] port = msg.getPort().getBytes(CharsetUtil.UTF_8);
        ByteBuf bodyBuffer =  Unpooled.buffer(ServerMessage.MessageEnum.getBodyLength() + address.length + port.length);
        bodyBuffer.writeShort(address.length);
        bodyBuffer.writeBytes(address);
        bodyBuffer.writeShort(port.length);
        bodyBuffer.writeBytes(port);
        bodyBuffer.writeByte(msg.getHeartbeat());

        msg.setBody(bodyBuffer.array());
        msg.getHeader().setBodyLength(msg.getBody().length);
        ReferenceCountUtil.release(bodyBuffer);
        out.add(msg);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, Message msg, List<Object> out) throws Exception {
        ServerResMessage message = new ServerResMessage(msg.getHeader());
        out.add(message);
    }
}
