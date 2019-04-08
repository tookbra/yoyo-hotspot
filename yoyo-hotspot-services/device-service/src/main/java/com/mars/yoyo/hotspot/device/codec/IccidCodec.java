package com.mars.yoyo.hotspot.device.codec;

import com.mars.yoyo.hotspot.device.packet.Message;
import com.mars.yoyo.hotspot.device.packet.protocol.IccidMessage;
import com.mars.yoyo.hotspot.device.packet.protocol.IccidResMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * iccid编解码器
 * @author tookbra
 * @date 2018/4/7
 * @description
 */
@Slf4j
public class IccidCodec extends MessageToMessageCodec<Message, IccidMessage> {
    @Override
    protected void encode(ChannelHandlerContext ctx, IccidMessage msg, List<Object> out) throws Exception {
        msg.setBody(new byte[0]);
        msg.getHeader().setBodyLength(msg.getBody().length);
        out.add(msg);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, Message msg, List<Object> out) throws Exception {
        IccidResMessage message = new IccidResMessage(msg.getHeader());

        ByteBuf bodyBuffer = Unpooled.wrappedBuffer(msg.getBody());
        message.setIccIdLen(bodyBuffer.readUnsignedByte());
        message.setIccId(bodyBuffer.readCharSequence(message.getIccIdLen(),
                CharsetUtil.UTF_8).toString().trim());

        ReferenceCountUtil.release(bodyBuffer);
        out.add(message);
    }
}
