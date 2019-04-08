package com.mars.yoyo.hotspot.device.packet.protocol;

import com.mars.yoyo.hotspot.device.packet.BoxPacketType;
import com.mars.yoyo.hotspot.device.packet.Header;
import com.mars.yoyo.hotspot.device.packet.Message;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 归还
 * @author tookbra
 * @date 2018/4/5
 * @description
 */
@Data
@ToString
public class ReturnMessage extends Message {

    private static final long serialVersionUID = -4661304669644924927L;

    /**
     * 槽位编号
     */
    private int slot;
    /**
     * 充电宝 ID
     */
    private String powerBankId;

    public ReturnMessage(Header header) {
        super(header);
    }


    public enum MessageEnum {

        SLOT(1),
        POWER_BANK_ID(8);

        private final static int bodyLength = SLOT.length + POWER_BANK_ID.length;

        private int length;

        MessageEnum(int length) {
            this.length = length;
        }

        public static int getBodyLength() {
            return bodyLength;
        }
    }
}
