package com.mars.yoyo.hotspot.admin.params;

import lombok.Data;

/**
 * 租借参数
 *
 * @author admin
 * @Date 2018/9/5 17:03
 */
@Data
public class LeaseParameter extends PageParameter {

    /**
     * 模块
     */
    private String powerBankId;

    /**
     * 租赁单号
     */
    private String leaseNo;

    /**
     * 是否归还
     */
    private Integer returned;

    /**
     * 起始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;

}
