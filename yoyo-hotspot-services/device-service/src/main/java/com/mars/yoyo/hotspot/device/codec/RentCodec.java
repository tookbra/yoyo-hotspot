package com.mars.yoyo.hotspot.device.codec;

import com.mars.yoyo.hotspot.device.packet.Message;
import com.mars.yoyo.hotspot.device.packet.protocol.RentMessage;
import com.mars.yoyo.hotspot.device.packet.protocol.RentResMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 租借编解码器
 * @author tookbra
 * @date 2018/4/7
 * @description
 */
@Slf4j
public class RentCodec extends MessageToMessageCodec<Message, RentMessage> {
    @Override
    protected void encode(ChannelHandlerContext ctx, RentMessage msg, List<Object> out) throws Exception {
        log.trace("rent encode={}");
        ByteBuf bodyBuffer =  Unpooled.buffer(RentMessage.MessageEnum.getBodyLength());
        bodyBuffer.writeByte(msg.getSlot());

        msg.setBody(bodyBuffer.array());
        msg.getHeader().setBodyLength(msg.getBody().length);
        ReferenceCountUtil.release(bodyBuffer);
        out.add(msg);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, Message msg, List<Object> out) throws Exception {
        log.trace("rent decode");
        RentResMessage rentResMessage = new RentResMessage(msg.getHeader());

        ByteBuf bodyBuffer = Unpooled.wrappedBuffer(msg.getBody());
        rentResMessage.setSlot(bodyBuffer.readUnsignedByte());
        rentResMessage.setResult(bodyBuffer.readUnsignedByte());
        rentResMessage.setPowerBankId(Long.toHexString(bodyBuffer.readLong()));
        ReferenceCountUtil.release(bodyBuffer);
        out.add(rentResMessage);
    }
}
