package com.mars.yoyo.hotspot.device.packet.protocol;

import com.mars.yoyo.hotspot.device.packet.Header;
import com.mars.yoyo.hotspot.device.packet.Message;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * 库存
 * @author tookbra
 * @date 2018/4/4
 * @description
 */
@Data
@NoArgsConstructor
@ToString
public class InventoryResMessage extends Message {
    private static final long serialVersionUID = 1729446511215855580L;
    /**
     * 剩余充电宝个数
     */
    private short remainNum;
    /**
     * 充电宝集合
     */
    private List<Inventory> inventoryList;

    public InventoryResMessage(Header header) {
        super(header);
    }
}
