package com.mars.yoyo.hotspot.device.packet.protocol;

import com.mars.yoyo.hotspot.device.packet.Header;
import com.mars.yoyo.hotspot.device.packet.Message;
import lombok.Data;
import lombok.ToString;

/**
 * 租借
 * @author tookbra
 * @date 2018/4/5
 * @description
 */
@Data
@ToString
public class RentResMessage extends Message {

    private static final long serialVersionUID = 3264377366473334435L;
    /**
     * 槽位编号
     */
    private short slot;
    /**
     * 借用结果: 0:失败 1:成功
     */
    private int result;
    /**
     * 充电宝 ID
     */
    private String powerBankId;

    public RentResMessage(Header header) {
        super(header);
    }

}
