package com.mars.yoyo.hotspot.device.packet.protocol;

import com.mars.yoyo.hotspot.device.packet.Header;
import com.mars.yoyo.hotspot.device.packet.Message;
import lombok.Data;
import lombok.ToString;

/**
 * 登陆包
 * @author tookbra
 * @date 2018/4/4
 * @description
 */
@Data
@ToString
public class ConnectMessage extends Message {
    private static final long serialVersionUID = 6680629341216662049L;

    /**
     * 随机数
     */
    private long rand;
    /**
     * 魔术字
     */
    private int magic;
    /**
     * BoxId的长度
     */
    private int boxIDLen;
    /**
     * 柜机id和柜身二维码对应
     */
    private String boxID;
    /**
     * 加密数据体长度
     */
    private long reqDataLen;
    /**
     * 加密数据体
     */
    private String reqData;

    public ConnectMessage(Header header) {
        super(header);
    }
}
