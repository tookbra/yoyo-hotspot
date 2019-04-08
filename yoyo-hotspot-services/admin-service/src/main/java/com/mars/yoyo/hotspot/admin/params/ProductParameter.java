package com.mars.yoyo.hotspot.admin.params;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 产品请求参数
 *
 * @author admin
 * @create 2018/5/20
 */
@Data
public class ProductParameter extends SessionParameter {

    private Integer id;

    /**
     * 产品名称
     */
    private String productName;

    /**
     * 产品类型 1小时借 2包天借 3包月借
     */
    private Integer productType;

    /**
     * 价格
     */
    private String price;

    /**
     * 是否启用 0否 1是
     */
    private Integer status;

    /**
     * 是否封顶 0否 1是
     */
    private Integer caped;

    /**
     *  封顶价格
     */
    private String capPrice;

    /**
     * 是否无限流量 0 否 1是
     */
    private Integer unlimited;

    /**
     * 最大流量
     */
    private String limited;

    /**
     * 是否送流量
     */
    private Integer gifted;

    /**
     * 流量大小
     */
    private String limitNum;

    private String giftMsg;

    /**
     * 描述
     */
    private String description;

    private String productNameEn;

    private BigDecimal priceEn;

    private String giftMsgEn;

    private String descriptionEn;

}
