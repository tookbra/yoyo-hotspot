package com.mars.yoyo.hotspot.device.codec;

import com.mars.yoyo.hotspot.device.packet.Message;
import com.mars.yoyo.hotspot.device.packet.protocol.ReturnMessage;
import com.mars.yoyo.hotspot.device.packet.protocol.ReturnResMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 归还编解码器
 * @author tookbra
 * @date 2018/4/6
 * @description
 */
@Slf4j
public class ReturnCodec extends MessageToMessageCodec<Message, ReturnResMessage> {
    @Override
    protected void encode(ChannelHandlerContext ctx, ReturnResMessage msg, List<Object> out) throws Exception {
        log.trace("encode return code");
        ByteBuf bodyBuffer =  Unpooled.buffer(ReturnResMessage.MessageEnum.getBodyLength());
        bodyBuffer.writeByte(msg.getSlot());
        bodyBuffer.writeByte(msg.getResult());

        msg.setBody(bodyBuffer.array());
        msg.getHeader().setBodyLength(msg.getBody().length);
        ReferenceCountUtil.release(bodyBuffer);
        out.add(msg);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, Message msg, List<Object> out) throws Exception {
        log.trace("decode return code");
        ReturnMessage returnMessage = new ReturnMessage(msg.getHeader());

        ByteBuf bodyBuffer = Unpooled.wrappedBuffer(msg.getBody());
        returnMessage.setSlot(bodyBuffer.readUnsignedByte());
        returnMessage.setPowerBankId(Long.toHexString(bodyBuffer.readLong()));

        ReferenceCountUtil.release(bodyBuffer);
        out.add(returnMessage);
    }
}
