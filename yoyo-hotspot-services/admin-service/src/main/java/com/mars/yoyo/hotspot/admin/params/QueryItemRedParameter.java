package com.mars.yoyo.hotspot.admin.params;

import lombok.Data;

/**
 * 查询小红包参数
 *
 * @author admin
 * @create 2018/9/1
 */
@Data
public class QueryItemRedParameter extends PageParameter {

    /**
     * 大红包
     */
    private Integer redEnvelopeId;

    /**
     * 是否被领取 1是0否
     */
    private Integer isReceive;

    /**
     * 金额
     */
    private String amount;

}
