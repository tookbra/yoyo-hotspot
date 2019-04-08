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
public class UsageNotifyDto implements Serializable {
    private static final long serialVersionUID = 3449088239167119487L;

    private String method;

    @JsonProperty("trans_id")
    private String transId;

    private String imei;

    /**
     * 设备总用量，单位是MB
     */
    @JsonProperty("total_usage")
    private double totalUsage;
    /**
     * 达量设置的值单位MB
     */
    @JsonProperty("usage_limit")
    private double usageLimit;

    private Date timestamp;
}
