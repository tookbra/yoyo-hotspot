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
public class SendMsgOutputDto {

    /**
     * 短信id
     */
    @JSONField(name = "msgid")
    private String msgId;
}
