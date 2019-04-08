package com.mars.yoyo.hotspot.device.packet;

import lombok.Data;

import java.io.Serializable;

/**
 * @author tookbra
 * @date 2018/4/4
 * @description
 */
@Data
public class Message implements Serializable {

    /**
     * 消息头
     */
    private Header header;
    /**
     * 消息体
     */
    private byte [] body;

    public Message() {
    }

    public Message(PacketType packetType) {
        Header header = new Header();
        header.setCommandId(packetType.getCommandId());
        this.setHeader(header);
    }

    public Message(Header header) {
        this.header = header;
    }

    public Message(PacketType packetType, Header header) {
        header.setCommandId(packetType.getCommandId());
        this.setHeader(header);
    }


}
