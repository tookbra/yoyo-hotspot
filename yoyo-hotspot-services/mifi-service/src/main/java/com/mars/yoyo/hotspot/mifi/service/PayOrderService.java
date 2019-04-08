package com.mars.yoyo.hotspot.mifi.service;

import com.mars.yoyo.hotspot.mifi.domain.PayOrder;

/**
 * @author tookbra
 * @date 2018/7/1
 * @description
 */
public interface PayOrderService {

    /**
     * 查询支付记录
     * @param orderNo 订单号
     * @return {@link PayOrder}
     */
    PayOrder findByOrderNo(String orderNo);
}
