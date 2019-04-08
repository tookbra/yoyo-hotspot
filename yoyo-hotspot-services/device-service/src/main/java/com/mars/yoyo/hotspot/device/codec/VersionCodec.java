package com.mars.yoyo.hotspot.device.codec;

import com.mars.yoyo.hotspot.device.packet.Message;
import com.mars.yoyo.hotspot.device.packet.protocol.VersionMessage;
import com.mars.yoyo.hotspot.device.packet.protocol.VersionResMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;

import java.util.List;

/**
 * 版本号编解码器
 * @author tookbra
 * @date 2018/4/6
 * @description
 */
public class VersionCodec extends MessageToMessageCodec<Message, VersionMessage> {
    @Override
    protected void encode(ChannelHandlerContext ctx, VersionMessage msg, List<Object> out) throws Exception {
        msg.setBody(new byte[0]);
        msg.getHeader().setBodyLength(msg.getBody().length);
        out.add(msg);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, Message msg, List<Object> out) throws Exception {
        VersionResMessage versionMessage = new VersionResMessage(msg.getHeader());

        ByteBuf bodyBuffer = Unpooled.wrappedBuffer(msg.getBody());
        versionMessage.setSoftVerLen(bodyBuffer.readUnsignedShort());
        versionMessage.setSoftVer(bodyBuffer.readCharSequence(versionMessage.getSoftVerLen(),
                CharsetUtil.UTF_8).toString().trim());

        ReferenceCountUtil.release(bodyBuffer);
        out.add(versionMessage);
    }
}
