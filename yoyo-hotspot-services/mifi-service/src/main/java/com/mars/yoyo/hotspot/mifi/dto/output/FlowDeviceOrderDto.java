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
public class FlowDeviceOrderDto extends FlowDeviceDto {

    /**
     * 套餐记录ID, uuid类型
     */
    @JsonProperty("record_id")
    private String recordId;
}
