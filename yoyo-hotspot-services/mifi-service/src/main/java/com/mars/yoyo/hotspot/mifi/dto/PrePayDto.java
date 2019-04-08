package com.mars.yoyo.hotspot.mifi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult;
import lombok.Data;

/**
 * @author tookbra
 * @date 2018/6/4
 * @description
 */
@Data
public class PrePayDto {

    private String redirectUrl;

    /**
     * 支付渠道
     */
    private String payChannel;
    /**
     * 订单号
     */
    private String orderNo;

    private boolean payResult = false;

    @JsonProperty("mpOrder")
    private WxPayMpOrderResult wxPayMpOrderResult;
}
