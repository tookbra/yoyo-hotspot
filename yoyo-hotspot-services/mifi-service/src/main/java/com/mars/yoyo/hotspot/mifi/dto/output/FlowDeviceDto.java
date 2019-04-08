package com.mars.yoyo.hotspot.mifi.dto.output;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author tookbra
 * @date 2018/10/28
 * @description
 */
@Data
@ToString
public class FlowDeviceDto implements Serializable {

    @JsonProperty("trans_id")
    private String transId;
}
