package com.mars.yoyo.hotspot.mifi.dto.output;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author tookbra
 * @date 2018/6/14
 * @description
 */
@Data
@ToString
public class DeviceItemOutput implements Serializable {
    private static final long serialVersionUID = 4223571067043284380L;

    /**
     * 充电宝id
     */
    private String powerBankId;
    /**
     * 设备id
     */
    private String deviceId;
    /**
     * 槽位
     */
    private short slot;
}
