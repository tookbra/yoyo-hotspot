package com.mars.yoyo.hotspot.admin.service;

import com.mars.yoyo.hotspot.admin.params.CommonParameter;
import com.mars.yoyo.hotspot.admin.resutls.UserRechargeView;
import com.mars.yoyo.hotspot.admin.resutls.base.ListResultEx;

/**
 * 帐号充值记录
 *
 * @author admin
 * @create 2018/6/6
 * @since 1.0.0
 */
public interface AccountRechangeSerivce {

    /**
     * 查询充值订单列表
     * @return
     */
    ListResultEx<UserRechargeView> getRechargeList(CommonParameter parameter);

}
