package com.mars.yoyo.hotspot.device.packet.protocol;

import com.mars.yoyo.hotspot.device.packet.Header;
import com.mars.yoyo.hotspot.device.packet.Message;
import lombok.Data;
import lombok.ToString;

/**
 * ICCID
 * @author tookbra
 * @date 2018/4/5
 * @description
 */
@Data
@ToString
public class IccidResMessage extends Message {

    private static final long serialVersionUID = 7334182028949424897L;

    /**
     * ICCID 的长度
     */
    private short iccIdLen;
    /**
     * SIM 卡的 ICCID
     */
    private String iccId;

    public IccidResMessage(Header header) {
        super(header);
    }
}
