package com.mars.yoyo.hotspot.mifi.service;

import com.mars.yoyo.hotspot.mifi.BaseTest;
import com.mars.yoyo.hotspot.mifi.domain.FlowOrder;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author tookbra
 * @date 2018/10/29
 * @description
 */
public class FlowOrderServiceTest extends BaseTest {

    @Autowired
    FlowOrderService flowOrderService;

    @Test
    public void enableFlowTest() {
        FlowOrder flowOrder = new FlowOrder();
        flowOrder.setPwd("123456");
        flowOrder.setImei("864319038825278");
        flowOrder.setTransId(String.valueOf(System.currentTimeMillis()));
        flowOrder.setLeaseId(2);
        String pkgId = "530";
        flowOrder.setPkgTypeId(pkgId);
        flowOrderService.enableFlow(flowOrder);
    }

    @Test
    public void findByLeaseIdTest() {
        flowOrderService.findByLeaseId(1);
    }
}
