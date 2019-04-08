package com.mars.yoyo.hotspot.device.packet.protocol;

import com.mars.yoyo.hotspot.device.packet.Header;
import com.mars.yoyo.hotspot.device.packet.Message;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 升级
 * @author tookbra
 * @date 2018/4/5
 * @description
 */
@Data
@NoArgsConstructor
public class UpgradeResMessage extends Message {
    private static final long serialVersionUID = 776448263299768711L;

    public UpgradeResMessage(Header header) {
        super(header);
    }
}
