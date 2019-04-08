package com.mars.yoyo.hotspot.mifi.pojo;

import lombok.Data;
import lombok.ToString;

/**
 * 微信支付-场景信息
 * @author tookbra
 * @date 2018/5/24
 * @description
 */
@Data
@ToString
public class SceneInfo {

    /**
     * 门店唯一标识
     */
    private String id;
    /**
     * 门店名称
     */
    private String name;
    /**
     * 门店行政区划码
     */
    private String areaCode;
    /**
     * 门店详细地址
     */
    private String address;
}
