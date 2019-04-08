package com.mars.yoyo.hotspot.cmp.config.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author tookbra
 * @date 2018/9/10
 * @description
 */
@Data
@ToString
public class AbstarctFlowDto implements Serializable {

    @JsonProperty("auth_token")
    private String authToken;
}
