package com.mars.yoyo.hotspot.cmp.config.rest.dto.output;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author tookbra
 * @date 2018/5/13
 * @description
 */
@Data
public class SendOrderOutputDto {

    @JSONField(name = "order_id")
    private String orderId;
}
