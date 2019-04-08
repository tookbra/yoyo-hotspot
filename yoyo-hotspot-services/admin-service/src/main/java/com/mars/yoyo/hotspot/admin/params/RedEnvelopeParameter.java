package com.mars.yoyo.hotspot.admin.params;

import lombok.Data;

/**
 * 红包请求参数
 *
 * @author admin
 * @create 2018/5/17
 */
@Data
public class RedEnvelopeParameter extends SessionParameter {

    private Integer id;

    /**
     * 金额
     */
    private String money;

    /**
     * 是否可用 1是 0 否
     */
    private Integer state;

    /**
     * 红包类型 1支付押金
     */
    private Integer channel;

    /**
     * 红包数量
     */
    private Integer count;

    /**
     * 是否已生成
     */
    private Integer isGenerate;

    /**
     * 是否国际版 1是 0 否
     */
    private Integer isEn;

    /**
     * 开始时间
     */
    private String beginTime;

    /**
     * 过期时间
     */
    private String endTime;



}
