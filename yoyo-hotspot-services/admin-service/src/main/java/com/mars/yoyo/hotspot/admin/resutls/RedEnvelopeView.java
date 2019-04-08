package com.mars.yoyo.hotspot.admin.resutls;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 红包结果参数
 *
 * @author admin
 * @create 2018/5/17
 */
@Data
@ToString
public class RedEnvelopeView implements Serializable {

    private Integer id;

    /**
     * 金额
     */
    private BigDecimal money;

    /**
     * 是否可用 1是 0 否
     */
    private Integer state;

    /**
     * 红包类型 1支付押金
     */
    private Integer channel;

    /**
     * 红包类型名称
     */
    private String channelName;

    /**
     * 红包数量
     */
    private Integer count;

    /**
     * 是否为国际版
     */
    private Integer isEn;

    /**
     * 是否已生成 1是 0否
     */
    private Integer isGenerate;

    /**
     * 开始时间
     */
    @JSONField(format = "yyyy-MM-dd")
    private Date beginTime;

    /**
     * 过期时间
     */
    @JSONField(format = "yyyy-MM-dd")
    private Date endTime;

}
