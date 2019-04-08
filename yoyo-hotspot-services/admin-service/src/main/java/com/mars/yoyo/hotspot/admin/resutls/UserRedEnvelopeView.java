package com.mars.yoyo.hotspot.admin.resutls;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户红包参数
 *
 * @author admin
 * @create 2018/5/16
 */
@Data
public class UserRedEnvelopeView implements Serializable {

    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 红包id
     */
    private Integer redEnvelopeId;

    /**
     * 红包
     */
    private Integer redItemId;

    /**
     * 金额
     */
    private BigDecimal amount;

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
     * 红包类型名称
     */
    private String channelName;

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
