package com.mars.yoyo.hotspot.cmp.config.rest.dto.input;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mars.yoyo.hotspot.cmp.config.rest.dto.AbstarctFlowDto;
import lombok.Data;
import lombok.ToString;

/**
 * @author tookbra
 * @date 2018/9/10
 * @description
 */
@Data
@ToString
public class DeviceUpdateDto extends AbstarctFlowDto {

    @JsonProperty("trans_id")
    private String transId;

    @JsonProperty("imei")
    private String imei;

    @JsonProperty("card_mode")
    private String cardMode;

    @JsonProperty("v_carrier")
    private String vCarrier;

    @JsonProperty("net_rate")
    private int netRate;

    @JsonProperty("usage_limit")
    private int usageLimit;
}
