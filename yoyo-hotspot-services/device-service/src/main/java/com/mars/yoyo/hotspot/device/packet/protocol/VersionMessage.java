package com.mars.yoyo.hotspot.device.packet.protocol;

import com.mars.yoyo.hotspot.device.packet.BoxPacketType;
import com.mars.yoyo.hotspot.device.packet.Header;
import com.mars.yoyo.hotspot.device.packet.Message;
import lombok.Data;

/**
 * 查询机柜软件版本号
 * @author tookbra
 * @date 2018/4/4
 * @description
 */
@Data
public class VersionMessage extends Message {

    private static final long serialVersionUID = -4986321525205688659L;


    public VersionMessage() {
        super(BoxPacketType.VERSION);
    }
    public VersionMessage(Header header) {
        super(BoxPacketType.VERSION,header);
    }
}
