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
public class SmsMsgStatusOutputDto {

    /**
     * 短信操作状态（0：未处理；1：已处理）
     */
    @JSONField(name = "status")
    private int status;
    /**
     * 短信状态
     */
    @JSONField(name = "state")
    private int state;
    /**
     * 短信状态描述
     */
    @JSONField(name = "message")
    private String message;
}
