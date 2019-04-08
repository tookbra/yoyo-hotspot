package com.mars.yoyo.hotspot.cmp.config.rest.dto.input;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NonNull;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author tookbra
 * @date 2018/5/12
 * @description
 */
@Data
@ToString
public class SendOrderDto implements Serializable {

    @JsonProperty("cardno")
    @NotNull
    private String cardNo;

    @JsonProperty("product_id")
    @NotNull
    private String productId;

    @JsonProperty("iseffect")
    private int iseffect;

    @JsonProperty("out_trade_no")
    private String outTradeNo;
}
