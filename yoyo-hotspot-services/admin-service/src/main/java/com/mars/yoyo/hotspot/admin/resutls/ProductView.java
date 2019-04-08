package com.mars.yoyo.hotspot.admin.resutls;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class ProductView implements Serializable {

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
    private BigDecimal price;

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
    private BigDecimal capPrice;

    /**
     * 是否无限流量 0 否 1是
     */
    private Integer unlimited;

    /**
     * 最大流量
     */
    private Integer limited;

    /**
     * 创建时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 是否送流量
     */
    private Integer gifted;

    /**
     * 流量大小
     */
    private Integer limitNum;

    private String giftMsg;

    /**
     * 描述
     */
    private String description;

    // 以下为英文
    private String productNameEn;

    private BigDecimal priceEn;

    private String giftMsgEn;

    private String descriptionEn;

}