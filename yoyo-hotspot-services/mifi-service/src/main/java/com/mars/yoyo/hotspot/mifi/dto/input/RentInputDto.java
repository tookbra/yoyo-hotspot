package com.mars.yoyo.hotspot.mifi.dto.input;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author tookbra
 * @date 2018/6/6
 * @description
 */
@Data
public class RentInputDto {

    /**
     * 支付渠道
     */
    @NotNull(message = "biz.pay.channel.not.found")
    private String payChannel;
    /**
     * 优惠券id
     */
    private Integer couponId;

    private Integer productId;

    private String path;

    private int currentId;

    private String orderNo;

    private int convert;
}
