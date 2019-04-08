package com.mars.yoyo.hotspot.device.packet.protocol;

import com.mars.yoyo.hotspot.device.packet.Header;
import com.mars.yoyo.hotspot.device.packet.Message;
import lombok.Data;
import lombok.ToString;

/**
 * 心跳包
 * @author tookbra
 * @date 2018/4/4
 * @description
 */
@Data
@ToString
public class HeartMessage extends Message {
    private static final long serialVersionUID = 5238896624270411059L;

    public HeartMessage() {

    }

    public HeartMessage(Header header) {
        super(header);
    }
}
