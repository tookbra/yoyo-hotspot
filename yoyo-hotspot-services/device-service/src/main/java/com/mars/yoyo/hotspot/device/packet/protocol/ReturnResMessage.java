package com.mars.yoyo.hotspot.device.packet.protocol;

import com.mars.yoyo.hotspot.device.packet.BoxPacketType;
import com.mars.yoyo.hotspot.device.packet.Message;
import lombok.Data;
import lombok.ToString;

/**
 * 归还
 * @author tookbra
 * @date 2018/4/5
 * @description
 */
@Data
@ToString
public class ReturnResMessage extends Message {

    private static final long serialVersionUID = -2850864031334756520L;

    /**
     * 槽位编号
     */
    private int slot;
    /**
     * 归还结果 0:归还失败(一般是服务 器状态有错误，比如服务 器访问不了数据库) 1:归还成功 2:充电宝状态异常 3:重复归还 4:非法充电宝 ID 5:归还的槽位不为空
     */
    private int result;

    public ReturnResMessage() {
        super(BoxPacketType.RETURN);
    }

    public enum MessageEnum {

        SLOT(1),
        RESULT(1);

        private final static int bodyLength = SLOT.length + RESULT.length;

        private int length;

        MessageEnum(int length) {
            this.length = length;
        }

        public static int getBodyLength() {
            return bodyLength;
        }
    }
}
