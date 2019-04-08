package com.mars.yoyo.hotspot.cmp.config.rest.dto.output;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.ToString;

/**
 * @author tookbra
 * @date 2018/5/13
 * @description
 */
@Data
@ToString
public class MarginOutputDto {

    /**
     * 物联网卡号
     */
    @JSONField(name = "cardno")
    private String cardNo;
    /**
     * 使用流量(-1表示未知，单位KB，时间当月)
     */
    @JSONField(name = "used")
    private int used;
    /**
     * 剩余流量(单位KB)
     */
    @JSONField(name = "surplus")
    private int surplus;
    /**
     * 物联网卡状态
     */
    @JSONField(name = "state")
    private String state;
    /**
     * 物联网卡状态描述
     */
    @JSONField(name = "message")
    private String message;
}
