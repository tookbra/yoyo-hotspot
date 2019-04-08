package com.mars.yoyo.hotspot.device.packet.protocol;

import com.mars.yoyo.hotspot.device.packet.Header;
import com.mars.yoyo.hotspot.device.packet.Message;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author tookbra
 * @date 2018/4/4
 * @description
 */
@Data
@NoArgsConstructor
public class ServerResMessage extends Message {
    private static final long serialVersionUID = -8971519798852243229L;

    public ServerResMessage(Header header) {
        super(header);
    }
}
