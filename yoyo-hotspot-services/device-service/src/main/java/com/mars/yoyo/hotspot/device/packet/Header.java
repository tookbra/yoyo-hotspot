package com.mars.yoyo.hotspot.device.packet;

import lombok.Data;

import java.io.Serializable;

/**
 * @author tookbra
 * @date 2018/4/4
 * @description
 */
@Data
public class Header implements Serializable {

    /**
     * 消息总长度(消息头+消息体)
     */
    private int totalLength;
    /**
     * 指令
     */
    private long commandId;
    /**
     * 版本号
     */
    private short version = 1;
    /**
     * 有效数据的字节异或
     */
    private short checkSum;
    /**
     * 会话令牌
     */
    private long token;
    /**
     * 消息头长度
     */
    private long headLength;
    /**
     * 消息体长度
     */
    private long bodyLength;

    /**
     * 消息头固定9长度
     * @return
     */
    public static long getHeadLength() {
        return 9L;
    }
}
