package com.mars.yoyo.hotspot.device.codec;

import com.mars.yoyo.hotspot.device.packet.Message;
import com.mars.yoyo.hotspot.device.packet.protocol.UpgradeMessage;
import com.mars.yoyo.hotspot.device.packet.protocol.UpgradeResMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 升级编解码器
 * @author tookbra
 * @date 2018/4/7
 * @description
 */
@Slf4j
public class UpgradeCodec extends MessageToMessageCodec<Message, UpgradeMessage> {
    @Override
    protected void encode(ChannelHandlerContext ctx, UpgradeMessage msg, List<Object> out) throws Exception {
        byte [] ftpAddress = msg.getFtpAddress().getBytes(CharsetUtil.UTF_8);
        byte [] ftpPort = msg.getFtpPort().getBytes(CharsetUtil.UTF_8);
        byte [] username = msg.getUsername().getBytes(CharsetUtil.UTF_8);
        byte [] password = msg.getPassword().getBytes(CharsetUtil.UTF_8);
        ByteBuf bodyBuffer =  Unpooled.buffer(UpgradeMessage.MessageEnum.getBodyLength()
                + ftpAddress.length + ftpPort.length + username.length + password.length);
        bodyBuffer.writeShort(ftpAddress.length);
        bodyBuffer.writeBytes(ftpAddress);
        bodyBuffer.writeShort(ftpPort.length);
        bodyBuffer.writeBytes(ftpPort);
        bodyBuffer.writeShort(username.length);
        bodyBuffer.writeBytes(username);
        bodyBuffer.writeShort(password.length);
        bodyBuffer.writeBytes(password);

        msg.setBody(bodyBuffer.array());
        msg.getHeader().setBodyLength(msg.getBody().length);
        ReferenceCountUtil.release(bodyBuffer);
        out.add(msg);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, Message msg, List<Object> out) throws Exception {
        UpgradeResMessage message = new UpgradeResMessage(msg.getHeader());
        out.add(message);
    }
}
