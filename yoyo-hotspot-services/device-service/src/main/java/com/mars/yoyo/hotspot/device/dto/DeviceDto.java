package com.mars.yoyo.hotspot.device.dto;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author tookbra
 * @date 2018/7/17
 * @description
 */
@Data
@ToString
public class DeviceDto implements Serializable {

    private BigDecimal lng;

    private BigDecimal lat;

    private String boxId;

    private String storeName;

    private String address;
}
