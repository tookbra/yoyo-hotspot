package com.mars.yoyo.hotspot.device.dto.input;

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
public class RentReportInput implements Serializable {
    private static final long serialVersionUID = 1482268298200637544L;

    /**
     * 充电宝id
     */
    private String powerBankId;
    /**
     * 设备id
     */
    private String deviceId;

    private short slot;

    private boolean success;
}
