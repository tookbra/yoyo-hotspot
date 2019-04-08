package com.mars.yoyo.hotspot.admin.params;

import lombok.Data;

/**
 * wifi参数
 *
 * @author admin
 * @create 2018/6/3
 */
@Data
public class DeviceItemParameter extends SessionParameter {

    private Integer id;

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
     * 对应设备id
     */
    private String deviceId;

    /**
     * 设备类型
     */
    private Integer itemType;

    /**
     * 是否被租借 1是 0否
     */
    private Integer leased;

}
