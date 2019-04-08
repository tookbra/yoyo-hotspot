package com.mars.yoyo.hotspot.device.packet.protocol;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author tookbra
 * @date 2018/4/7
 * @description
 */
@Data
@ToString
public class Inventory implements Serializable {
    private static final long serialVersionUID = 1353488546479304542L;

    /**
     * 槽位编号
     */
    private short slot;
    /**
     * 充电宝 ID
     */
    private String powerBandId;
    /**
     * 充电宝电量 0,1,2,3,4
     */
    private short level;
}
