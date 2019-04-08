package com.mars.yoyo.hotspot.admin.resutls;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class RedEnvelopeItemView {

    private Integer id;

    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 红包id
     */
    private Integer redEnvelopeId;

    /**
     * 总金额
     */
    private BigDecimal money;

    /**
     * 是否被领取 1是0否
     */
    private Integer received;

    /**
     * 是否可用 1是 0 否
     */
    private Integer state;

    /**
     * 红包类型 1支付押金
     */
    private Integer channel;

    /**
     * 开始时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date beginTime;

    /**
     * 过期时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

}