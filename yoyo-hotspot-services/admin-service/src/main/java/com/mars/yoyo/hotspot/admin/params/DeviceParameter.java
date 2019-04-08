package com.mars.yoyo.hotspot.admin.params;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 设备参数
 *
 * @author admin
 * @create 2018/5/10
 */
@Data
public class DeviceParameter extends SessionParameter {

    private Integer id;

    /**
     * 机柜编号
     */
    private String boxId;

    /**
     * 机柜版本号
     */
    private String version;

    /**
     * 剩余充电宝个数
     */
    private Integer remainNum;

    /**
     * 投放渠道
     */
    private Integer deliveryChannel;

    /**
     * 上次心跳时间
     */
    private String lastHeart;

    /**
     * 服务器地址
     */
    private String serverAddress;

    /**
     * 服务器端口
     */
    private Integer serverPort;

    /**
     * 精度
     */
    private BigDecimal longitude;

    /**
     * 纬度
     */
    private BigDecimal latitude;

    /**
     * 存放地址
     */
    private String address;

}
