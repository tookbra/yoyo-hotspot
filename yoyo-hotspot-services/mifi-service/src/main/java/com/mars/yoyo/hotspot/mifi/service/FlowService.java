package com.mars.yoyo.hotspot.mifi.service;

import com.mars.yoyo.hotspot.mifi.dto.AbstractFlowDto;
import com.mars.yoyo.hotspot.mifi.dto.output.FlowDeviceOrderDto;
import com.mars.yoyo.hotspot.mifi.pojo.DeviceFlow;

import java.util.List;

/**
 * @author tookbra
 * @date 2018/9/10
 * @description
 */
public interface FlowService {

    /**
     * 设备操作
     * @param deviceFlow
     */
    void deviceUpdate(DeviceFlow deviceFlow);

    /**
     * 查询设备用量
     * @param imei 设备imei
     * @param monthList 月份 ex.201801
     */
    void cdrDevice(String imei, List<String> monthList);

    /**
     * 查询整体用量
     * @param monthList 月份 ex.201801
     */
    void cdrBrand(List<String> monthList);

    /**
     * 套餐购买
     * @param imei 设备imei
     * @param pkgTypeId 套餐类型ID
     * @return true or false
     */
    AbstractFlowDto<FlowDeviceOrderDto> pkgOrder(String imei, String pkgTypeId);

    /**
     * 套餐失效
     * @param imei 设备imei
     * @param recordId 套餐记录ID
     * @return true or false
     */
    boolean pkgRecord(String imei, String recordId);
}
