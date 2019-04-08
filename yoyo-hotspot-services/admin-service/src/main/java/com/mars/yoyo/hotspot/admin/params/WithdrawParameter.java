package com.mars.yoyo.hotspot.admin.params;

import lombok.Data;

/**
 * 提现状态修改参数
 *
 * @author admin
 * @create 2018/6/25
 */
@Data
public class WithdrawParameter extends SessionParameter {

    /**
     * 提现Id
     */
    private Integer id;

    /**
     * 提现状态
     */
    private Integer status;


}
