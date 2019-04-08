package com.mars.yoyo.hotspot.mifi.vo;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author tookbra
 * @date 2018/7/13
 * @description
 */
@Data
@ToString
public class PreLeaseVo implements Serializable {

    /**
     * 产品名称
     */
    private String productName;
    /**
     * 产品单价
     */
    private BigDecimal price;
    /**
     * 产品类型
     */
    private int productType;
    /**
     * 租赁时间
     */
    private Date rentTime;
    /**
     * 应付金额
     */
    private BigDecimal payAmount;
    /**
     * 当前租赁详情
     */
    private LeaseDetailVo currentDetail;

    private int usedMin;
}
