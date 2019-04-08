package com.mars.yoyo.hotspot.admin.resutls;

import lombok.Data;

import java.io.Serializable;

/**
 * wifi设备信息
 *
 * @author admin
 * @create 2018/6/3
 */
@Data
public class DeviceItemView implements Serializable {

    private static final long serialVersionUID = 6655142830519090073L;

    private Integer id;

    /**
     * 机柜版本号
     */
    private String version;

    /**
     * 充电宝id
     */
    private String powerBankId;

    /**
     * 槽位编号
     */
    private Integer slot;

    /**
     * 0 20%电量 1 40%电量 2 60%电量 3 80%电量 4 100%电量
     */
    private Integer level;

    /**
     * 流量状态(当前不展示)
     */
    private Integer flowState;

    /**
     * 对应设备id
     */
    private String deviceId;

    /**
     * 设备类型
     */
    private Integer itemType;

    /**
     * 设置类型名称
     */
    private String itemTypeName;

    /**
     * 是否被租借 1是 0否
     */
    private Integer leased;

}
