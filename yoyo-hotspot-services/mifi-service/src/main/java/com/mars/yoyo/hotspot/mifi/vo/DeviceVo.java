package com.mars.yoyo.hotspot.mifi.vo;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author tookbra
 * @date 2018/7/24
 * @description
 */
@Data
@ToString
public class DeviceVo implements Serializable {

    /**
     * 设备是否在线
     */
    private boolean deviceOnline;
    /**
     * 是否有模块
     */
    private boolean deviceItem;

    private String address;

    private String addressEn;
}
