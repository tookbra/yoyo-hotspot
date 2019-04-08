package com.mars.yoyo.hotspot.mifi.task;

import com.mars.yoyo.hotspot.mifi.domain.FlowOrder;
import com.mars.yoyo.hotspot.mifi.domain.Lease;
import com.mars.yoyo.hotspot.mifi.service.FlowOrderService;
import com.mars.yoyo.hotspot.mifi.service.LeaseService;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author tookbra
 * @date 2018/10/29
 * @description
 */
@Slf4j
@Component
public class FlowTask {

    @Autowired
    LeaseService leaseService;

    @Autowired
    FlowOrderService flowOrderService;


    /**
     * 流量关闭
     */
    @Scheduled(cron="0 0/3 * * * ?")
    @Transactional(rollbackFor = Exception.class)
    public void flowClose() {
        List<Lease> leases = leaseService.findNotOver();
        Date now = new Date();
        leases.forEach(lease -> {
            List<FlowOrder> flowOrders = new ArrayList<>();
            if(lease.getExpectedReturnTime() != null) {
                if(now.after(lease.getExpectedReturnTime())) {
                    flowOrders = flowOrderService.findByLeaseId(lease.getId());
                }
            } else {
                BigDecimal amount = leaseService.calculatedAmount(lease.getLangEn(), lease.getProductType(),
                        lease.getRentTime(), now);
                if(amount.intValue() > 30 && !lease.getOvered()) {
                    flowOrders = flowOrderService.findByLeaseId(lease.getId());
                }
            }

            flowOrders.forEach(flowOrder -> {
                flowOrderService.closeFlow(flowOrder);
            });
        });

    }
}
