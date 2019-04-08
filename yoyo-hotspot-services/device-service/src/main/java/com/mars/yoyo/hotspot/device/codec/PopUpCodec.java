package com.mars.yoyo.hotspot.device.codec;

import com.mars.yoyo.hotspot.device.packet.Message;
import com.mars.yoyo.hotspot.device.packet.protocol.PopUpMessage;
import com.mars.yoyo.hotspot.device.packet.protocol.PopUpResMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import io.netty.util.ReferenceCountUtil;

import java.util.List;

/**
 * 弹出编解码器
 * @author tookbra
 * @date 2018/4/7
 * @description
 */
public class PopUpCodec extends MessageToMessageCodec<Message, PopUpMessage> {
    @Override
    protected void encode(ChannelHandlerContext ctx, PopUpMessage msg, List<Object> out) throws Exception {
        ByteBuf bodyBuffer =  Unpooled.buffer(PopUpMessage.MessageEnum.getBodyLength());
        bodyBuffer.writeByte(msg.getSlot());

        msg.setBody(bodyBuffer.array());
        msg.getHeader().setBodyLength(msg.getBody().length);
        ReferenceCountUtil.release(bodyBuffer);
        out.add(msg);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, Message msg, List<Object> out) throws Exception {
        PopUpResMessage message = new PopUpResMessage(msg.getHeader());

        ByteBuf bodyBuffer = Unpooled.wrappedBuffer(msg.getBody());
        message.setSlot(bodyBuffer.readUnsignedByte());
        message.setResult(bodyBuffer.readUnsignedByte());
        message.setPowerBankID(bodyBuffer.readLong());

        ReferenceCountUtil.release(bodyBuffer);
        out.add(message);
    }
}
