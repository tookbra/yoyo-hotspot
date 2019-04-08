package com.mars.yoyo.hotspot.mifi.service.impl;

import com.mars.yoyo.hotspot.mifi.dao.FlowOrderMapper;
import com.mars.yoyo.hotspot.mifi.domain.Coupon;
import com.mars.yoyo.hotspot.mifi.domain.FlowOrder;
import com.mars.yoyo.hotspot.mifi.domain.Lease;
import com.mars.yoyo.hotspot.mifi.dto.AbstractFlowDto;
import com.mars.yoyo.hotspot.mifi.dto.output.FlowDeviceOrderDto;
import com.mars.yoyo.hotspot.mifi.enums.FlowPackageEnum;
import com.mars.yoyo.hotspot.mifi.pojo.DeviceFlow;
import com.mars.yoyo.hotspot.mifi.service.FlowOrderService;
import com.mars.yoyo.hotspot.mifi.service.FlowService;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * @author tookbra
 * @date 2018/10/29
 * @description
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class, readOnly = true)
public class FlowOrderServiceImpl implements FlowOrderService {

    @Autowired
    FlowOrderMapper flowOrderMapper;

    @Autowired
    FlowService flowService;

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public void enableFlow(FlowOrder flowOrder) {
        DeviceFlow deviceFlow = new DeviceFlow();
        deviceFlow.setWifiPwd(flowOrder.getPwd());
        deviceFlow.setImei(flowOrder.getImei());
        flowService.deviceUpdate(deviceFlow);

        AbstractFlowDto<FlowDeviceOrderDto> flowDto = flowService.pkgOrder(flowOrder.getImei(), flowOrder.getPkgTypeId());
        if(flowDto.isSuccess()) {
            flowOrder.setStoped(false);
            flowOrder.setRecordId(flowDto.getData().getRecordId());
            flowOrderMapper.insertUseGeneratedKeys(flowOrder);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public void update(FlowOrder flowOrder) {
        flowOrderMapper.updateByPrimaryKey(flowOrder);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public void closeFlow(FlowOrder flowOrder) {
        boolean result = flowService.pkgRecord(flowOrder.getImei(), flowOrder.getRecordId());
        if(result) {
            flowOrder.setStoped(true);
            flowOrder.setModifyTime(DateTime.now().toDate());
            DeviceFlow deviceFlow = new DeviceFlow();
            deviceFlow.setImei(flowOrder.getImei());
            deviceFlow.setWifiCtl("disable");
            flowService.deviceUpdate(deviceFlow);
        }
        this.update(flowOrder);
    }

    @Override
    public void closeFlow(int leaseId) {
        List<FlowOrder> flowOrders = this.findByLeaseId(leaseId);
        flowOrders.forEach(flowOrder -> {
            flowService.pkgRecord(flowOrder.getImei(), flowOrder.getRecordId());
        });
    }

    @Override
    public List<FlowOrder> findByLeaseId(int leaseId) {
        Example example = new Example(FlowOrder.class);
        example.createCriteria().andEqualTo("leaseId", leaseId)
                .andEqualTo("stoped", 0);

        return flowOrderMapper.selectByExample(example);
    }

    @Override
    public List<FlowOrder> findByImei(String imei) {
        Example example = new Example(FlowOrder.class);
        example.createCriteria().andEqualTo("imei", imei)
                .andEqualTo("stoped", 0);

        return flowOrderMapper.selectByExample(example);
    }

    @Override
    public String convertPkgId(Lease lease) {
        String pkgId = "";
        if(lease.getLangEn() == null) {
            lease.setLangEn(false);
        }
        if(lease.getLangEn()) {
            switch (lease.getProductType()) {
                case 1:
                    pkgId = FlowPackageEnum.HOUR_EN.getPkgId().toString();
                    break;
                case 2:
                    pkgId = FlowPackageEnum.DAY_EN.getPkgId().toString();
                    break;
                case 3:
                    pkgId = FlowPackageEnum.MONTH_EN.getPkgId().toString();
                    break;
                default:
                    break;
            }
        } else {
            switch (lease.getProductType()) {
                case 1:
                    pkgId = FlowPackageEnum.HOUR.getPkgId().toString();
                    break;
                case 2:
                    pkgId = FlowPackageEnum.DAY.getPkgId().toString();
                    break;
                case 3:
                    pkgId = FlowPackageEnum.MONTH.getPkgId().toString();
                    break;
                default:
                    break;
            }
        }
        return pkgId;
    }
}
