package com.mars.yoyo.hotspot.admin.service;

import com.mars.yoyo.hotspot.admin.params.LeaseParameter;
import com.mars.yoyo.hotspot.admin.resutls.LeaseView;
import com.mars.yoyo.hotspot.admin.resutls.base.ListResultEx;

/**
 * 租借
 *
 * @author admin
 * @Date 2018/9/5 17:02
 */
public interface LeaseService {


    /**
     * 租借订单
     * @param parameter
     * @return
     */
    ListResultEx<LeaseView> listLeaseView(LeaseParameter parameter);


}
