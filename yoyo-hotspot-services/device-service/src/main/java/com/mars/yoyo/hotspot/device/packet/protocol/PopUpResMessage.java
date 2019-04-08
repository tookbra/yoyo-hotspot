package com.mars.yoyo.hotspot.device.packet.protocol;

import com.mars.yoyo.hotspot.device.packet.Header;
import com.mars.yoyo.hotspot.device.packet.Message;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 弹出
 * @author tookbra
 * @date 2018/4/5
 * @description
 */
@Data
@NoArgsConstructor
@ToString
public class PopUpResMessage extends Message {
    private static final long serialVersionUID = 6897142604940251340L;

    /**
     * 槽位编号
     */
    private int slot;
    /**
     * 借用结果: 0:失败 1:成功
     */
    private int result;
    /**
     * 充电宝 ID
     */
    private long powerBankID;

    public PopUpResMessage(Header header) {
        super(header);
    }
}
