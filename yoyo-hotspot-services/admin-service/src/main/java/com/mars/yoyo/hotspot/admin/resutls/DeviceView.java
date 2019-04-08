package com.mars.yoyo.hotspot.admin.resutls;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 设备参数
 *
 * @author admin
 * @create 2018/6/3
 */
@Data
public class DeviceView implements Serializable {

    private static final long serialVersionUID = 5757319164736972923L;

    private Integer id;

    /**
     * 机柜ID
     */
    private String boxId;

    /**
     * 机柜版本号
     */
    private String version;

    /**
     * 设备数量
     */
    private Integer total;

    /**
     * 剩余充电宝个数
     */
    private Integer remainNum;

    /**
     * 投放渠道
     */
    private Integer deliveryChannel;

    /**
     * 渠道名称
     */
    private String deliveryName;

    /**
     * 上次心跳时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastHeart;

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

    /**
     * 是否移除：0-未删除，1-已删除
     */
    private Integer deleted;

    private String addressEn;

    private String addressDetail;

    private String addressDetailEn;

}
