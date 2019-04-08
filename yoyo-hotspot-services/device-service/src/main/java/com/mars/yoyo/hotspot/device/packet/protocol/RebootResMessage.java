package com.mars.yoyo.hotspot.device.packet.protocol;

import com.mars.yoyo.hotspot.device.packet.Header;
import com.mars.yoyo.hotspot.device.packet.Message;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 重启
 * @author tookbra
 * @date 2018/4/5
 * @description
 */
@Data
@NoArgsConstructor
@ToString
public class RebootResMessage extends Message {

    public RebootResMessage(Header header) {
        super(header);
    }
}
