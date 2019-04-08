package com.mars.yoyo.hotspot.mifi.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author tookbra
 * @date 2018/5/15
 * @description
 */
@Data
public class CurrentProductVo {

    private int id;
    /**
     * 产品名称
     */
    @JsonProperty("name")
    private String productName;
    /**
     * 产品类型 1小时借 2包天借 3包月借'
     */
    @JsonProperty("type")
    private int productType;
    /**
     * 价格
     */
    private BigDecimal price;
    /**
     * 描述
     */
    private String description;
    /**
     * 是否无限流量
     */
    private Boolean unlimited;
    /**
     * 是否送流量
     */
    private boolean gifted;

    private String giftContent;
}
