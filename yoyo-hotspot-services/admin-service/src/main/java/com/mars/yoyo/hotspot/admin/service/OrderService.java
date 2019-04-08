package com.mars.yoyo.hotspot.admin.service;

import com.mars.yoyo.hotspot.admin.params.OrderParameter;
import com.mars.yoyo.hotspot.admin.resutls.PayOrderView;
import com.mars.yoyo.hotspot.admin.resutls.base.ListResultEx;

/**
 * 订单接口
 *
 * @author admin
 * @create 2018/5/14
 * @since 1.0.0
 */
public interface OrderService {

    /**
     * 查询订单列表
     * @param parameter
     * @return
     */
    ListResultEx<PayOrderView> getOrderList(OrderParameter parameter);

}
