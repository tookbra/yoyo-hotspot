package com.mars.yoyo.hotspot.ms.dto.output;

import lombok.Data;
import lombok.ToString;

/**
 * @author tookbra
 * @date 2018/5/15
 * @description
 */
@Data
@ToString
public class SmsReqOutput {

    private String reqCode;

    private String reqMsg;

    private String reqId;
}
