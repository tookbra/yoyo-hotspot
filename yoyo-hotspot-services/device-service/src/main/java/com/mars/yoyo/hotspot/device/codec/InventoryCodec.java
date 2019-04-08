package com.mars.yoyo.hotspot.device.codec;

import com.mars.yoyo.hotspot.device.packet.Message;
import com.mars.yoyo.hotspot.device.packet.protocol.Inventory;
import com.mars.yoyo.hotspot.device.packet.protocol.InventoryMessage;
import com.mars.yoyo.hotspot.device.packet.protocol.InventoryResMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * 库存编解码器
 * @author tookbra
 * @date 2018/4/6
 * @description
 */
@Slf4j
public class InventoryCodec extends MessageToMessageCodec<Message, InventoryMessage> {
    @Override
    protected void encode(ChannelHandlerContext ctx, InventoryMessage msg, List<Object> out) throws Exception {
        msg.setBody(new byte[0]);
        msg.getHeader().setBodyLength(msg.getBody().length);
        out.add(msg);
        log.info("开始查询库存");
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, Message msg, List<Object> out) throws Exception {
        InventoryResMessage inventoryResMessage = new InventoryResMessage(msg.getHeader());

        ByteBuf bodyBuffer = Unpooled.wrappedBuffer(msg.getBody());
        inventoryResMessage.setRemainNum(bodyBuffer.readUnsignedByte());
        List<Inventory> inventoryList = new ArrayList<>();
        log.info("收到查询库存返回报文");
        log.info("收到查询库存返回报文, 库存={}", inventoryResMessage.getRemainNum());
        for(int i = 0; i < inventoryResMessage.getRemainNum(); i++) {
            Inventory inventory = new Inventory();
            inventory.setSlot(bodyBuffer.readUnsignedByte());
            long bcd = bodyBuffer.readLong();
            log.info("bcd={}", bcd);
            inventory.setPowerBandId(Long.toHexString(bcd));
            inventory.setLevel(bodyBuffer.readUnsignedByte());
            inventoryList.add(inventory);
        }
        inventoryResMessage.setInventoryList(inventoryList);
        ReferenceCountUtil.release(bodyBuffer);
        out.add(inventoryResMessage);
    }
}
