package com.mars.yoyo.hotspot.mifi.service;

import com.mars.yoyo.hotspot.mifi.domain.FlowOrder;
import com.mars.yoyo.hotspot.mifi.domain.Lease;

import java.util.List;

/**
 * @author tookbra
 * @date 2018/10/29
 * @description
 */
public interface FlowOrderService {

    /**
     *
     * @param flowOrder
     */
    void enableFlow(FlowOrder flowOrder);

    /**
     * 更新流量
     * @param flowOrder
     */
    void update(FlowOrder flowOrder);

    /**
     * 关闭流量
     * @param flowOrder
     */
    void closeFlow(FlowOrder flowOrder);

    /**
     * 关闭流量
     * @param leaseId
     */
    void closeFlow(int leaseId);

    /**
     *
     * @param leaseId
     * @return
     */
    List<FlowOrder> findByLeaseId(int leaseId);

    /**
     *
     * @param imei
     * @return
     */
    List<FlowOrder> findByImei(String imei);

    /**
     * 套餐id
     * @param lease
     * @return
     */
    String convertPkgId(Lease lease);
}
