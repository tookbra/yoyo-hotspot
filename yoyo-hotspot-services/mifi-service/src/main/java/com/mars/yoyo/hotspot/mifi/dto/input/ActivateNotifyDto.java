package com.mars.yoyo.hotspot.mifi.dto.input;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @author tookbra
 * @date 2018/9/11
 * @description
 */
@Data
@ToString
public class ActivateNotifyDto implements Serializable {
    private static final long serialVersionUID = 3449088239167119487L;

    private String method;

    @JsonProperty("trans_id")
    private String transId;

    private String imei;

    /**
     * 设备端获取的imsi
     */
    private String imsi;

    /**
     * 数据库当前卡模式, v为虚拟卡，p为实体卡
     * 注意：这个不是设备当前上传时的模式，而是当前后端配置的模式，设备最终会切换到这个模式
     */
    @JsonProperty("db_card_mode")
    private String dbCardMode;
    /**
     * 设备当前的卡模式
     */
    @JsonProperty("dev_card_mode")
    private String devCardMode;
    /**
     * 当没有实体卡时，iccid1是虚拟卡的iccid, 此时iccid2无效；
     * 当有实体卡时，如果设备是实体卡模式，iccid1是实体卡的iccid, iccid2无效;
     * 当有实体卡时，如果设备是虚拟卡模式，iccid1是实体卡的iccid, iccid2是虚拟卡的iccid;
     */
    private String iccid1;

    private String iccid2;

    /**
     * 首次通知时间戳，字符串
     */
    private Date timestamp;
}
