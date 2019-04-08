package com.mars.yoyo.hotspot.device.codec;

import com.mars.yoyo.hotspot.device.packet.Message;
import com.mars.yoyo.hotspot.device.packet.protocol.ConnectMessage;
import com.mars.yoyo.hotspot.device.packet.protocol.ConnectResMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 登陆编解码器
 * @author tookbra
 * @date 2018/4/6
 * @description
 */
@Slf4j
public class ConnectCodec extends MessageToMessageCodec<Message, ConnectResMessage> {
    @Override
    protected void encode(ChannelHandlerContext ctx, ConnectResMessage msg, List<Object> out) throws Exception {
        ByteBuf bodyBuffer =  Unpooled.buffer(ConnectResMessage.MessageEnum.getBodyLength());
        bodyBuffer.writeByte(msg.getStatus());

        msg.setBody(bodyBuffer.array());
        msg.getHeader().setBodyLength(msg.getBody().length);
        ReferenceCountUtil.release(bodyBuffer);
        out.add(msg);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, Message msg, List<Object> out) throws Exception {
        ConnectMessage connectMessage = new ConnectMessage(msg.getHeader());

        ByteBuf bodyBuffer = Unpooled.wrappedBuffer(msg.getBody());
        connectMessage.setRand(bodyBuffer.readUnsignedInt());
        connectMessage.setMagic(bodyBuffer.readUnsignedShort());
        int boxIdLen = bodyBuffer.readUnsignedShort();
        connectMessage.setBoxIDLen(boxIdLen);
        log.info("boxIdLeb={}", boxIdLen);
        connectMessage.setBoxID(bodyBuffer.readCharSequence(boxIdLen,
                CharsetUtil.UTF_8).toString().trim());

        ReferenceCountUtil.release(bodyBuffer);
        out.add(connectMessage);
    }
}
