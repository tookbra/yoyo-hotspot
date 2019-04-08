package com.mars.yoyo.hotspot.device.codec;

import com.mars.yoyo.hotspot.device.packet.Header;
import com.mars.yoyo.hotspot.device.packet.Message;
import com.mars.yoyo.hotspot.util.DateUtil;
import com.mars.yoyo.hotspot.util.StringUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 头编解码器
 * @author tookbra
 * @date 2018/4/4
 * @description
 */
@Slf4j
public class HeaderCodec extends MessageToMessageCodec<ByteBuf, Message> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, List<Object> out) throws Exception {
        log.info("begin to encode package");
        long headerLength = Header.getHeadLength();
        int packetLength = (int)(msg.getBody().length + headerLength);

        Header header = msg.getHeader();
        ByteBuf buf = ctx.alloc().buffer(packetLength);
        buf.writeShort(packetLength);
        buf.writeByte((short)header.getCommandId());
        buf.writeByte(header.getVersion());
        buf.writeByte(StringUtil.getXor(msg.getBody()));
        buf.writeInt(DateUtil.getTimeStamp());

        buf.writeBytes(msg.getBody());
        out.add(buf);
        log.info("end to encode package");
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        Header header = new Header();
        header.setTotalLength(msg.readUnsignedShort() + 2);
        header.setCommandId(msg.readUnsignedByte());
        header.setVersion(msg.readUnsignedByte());
        header.setCheckSum(msg.readUnsignedByte());
        header.setToken(msg.readInt());

        header.setHeadLength(Header.getHeadLength());
        header.setBodyLength(header.getTotalLength() - Header.getHeadLength());

        Message message = new Message(header);
        if (header.getBodyLength() > 0) {
            message.setBody(new byte[(int)header.getBodyLength()]);

            assert(header.getBodyLength() == msg.readableBytes());

            msg.readBytes(message.getBody());
        } else {
            message.setBody(new byte[0]);
        }
        out.add(message);
    }
}
