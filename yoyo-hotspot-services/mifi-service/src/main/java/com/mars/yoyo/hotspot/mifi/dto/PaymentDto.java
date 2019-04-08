package com.mars.yoyo.hotspot.mifi.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author tookbra
 * @date 2018/6/22
 * @description
 */
@Data
@ToString
public class PaymentDto implements Serializable {

    private String currentOrderNo;

    private String currentRentNo;

    private Integer currentProductId;

    private Integer productId;

    private String orderNo;

    private String lang;

    private String channelId;

    private String payType;

    private BigDecimal amount;

    private transient int userId;

    private Integer payChannel;

    private boolean maturity;
}
