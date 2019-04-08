package com.mars.yoyo.hotspot.admin.service;

import com.mars.yoyo.hotspot.admin.params.CommonParameter;
import com.mars.yoyo.hotspot.admin.params.WithdrawParameter;
import com.mars.yoyo.hotspot.admin.resutls.UserAccountWithdrawView;
import com.mars.yoyo.hotspot.admin.resutls.base.ListResultEx;
import com.mars.yoyo.hotspot.admin.resutls.base.ResultEx;
import com.mars.yoyo.hotspot.result.RestResult;

/**
 * 提现
 *
 * @author admin
 * @create 2018/6/25
 */
public interface AccountWithdrawService {

    /**
     * 分页提现记录
     * @return
     */
    ListResultEx<UserAccountWithdrawView> getAccountWithdraws(CommonParameter parameter);


    /**
     * 设置提现状态
     * @param parameter
     * @return
     */
    RestResult updateStatusById(WithdrawParameter parameter);


}
