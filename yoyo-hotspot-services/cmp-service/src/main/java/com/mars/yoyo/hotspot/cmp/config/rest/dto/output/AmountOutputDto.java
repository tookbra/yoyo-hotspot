package com.mars.yoyo.hotspot.cmp.config.rest.dto.output;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * @author tookbra
 * @date 2018/5/13
 * @description
 */
@Data
@ToString
public class AmountOutputDto {

    @JSONField(name = "amount")
    private BigDecimal amount;
}
