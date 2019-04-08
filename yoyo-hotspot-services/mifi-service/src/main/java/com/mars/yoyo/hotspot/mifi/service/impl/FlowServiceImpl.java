package com.mars.yoyo.hotspot.mifi.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mars.yoyo.hotspot.mifi.config.FlowProperties;
import com.mars.yoyo.hotspot.mifi.dto.AbstractFlowDto;
import com.mars.yoyo.hotspot.mifi.dto.output.FlowDeviceDto;
import com.mars.yoyo.hotspot.mifi.dto.output.FlowDeviceOrderDto;
import com.mars.yoyo.hotspot.mifi.dto.output.FlowDeviceUpdateDto;
import com.mars.yoyo.hotspot.mifi.pojo.DeviceFlow;
import com.mars.yoyo.hotspot.mifi.service.FlowService;
import com.mars.yoyo.hotspot.mifi.utils.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.util.http.SimpleGetRequestExecutor;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;


/**
 * @author tookbra
 * @date 2018/9/10
 * @description
 */
@Slf4j
@Service
public class FlowServiceImpl implements FlowService {

    @Autowired
    FlowProperties flowProperties;

    final ObjectMapper mapper = new ObjectMapper();

    private static final String FLOW_SUCCESS = "200";

    @Override
    public void deviceUpdate(DeviceFlow deviceFlow) {
        deviceFlow.setAuthToken(flowProperties.getAuthToken());
        String result = HttpUtils.post(flowProperties.getRequestUrl().getDeviceUpdate(), deviceFlow.toMap());
        log.info("send mefi card update, result={}", result);
        try {
            AbstractFlowDto<FlowDeviceUpdateDto> flowDeviceUpdateDto = mapper.readValue(result, new TypeReference<AbstractFlowDto<FlowDeviceUpdateDto>>() {});
            if(!flowDeviceUpdateDto.getCode().equals(FLOW_SUCCESS)) {

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void cdrDevice(String imei, List<String> monthList) {
        Map<String, String> map = new HashMap<>(5);
        map.put("auth_token", flowProperties.getAuthToken());
        map.put("trans_id", String.valueOf(System.currentTimeMillis()));
        map.put("imei", imei);
        if(CollectionUtils.isNotEmpty(monthList)) {
            monthList.forEach(month -> {
                map.put("month_ary", month);
            });
        }
        String result = HttpUtils.get(flowProperties.getRequestUrl().getCdrDevice(), map);
    }

    @Override
    public void cdrBrand(List<String> monthList) {
        Map<String, String> map = new HashMap<>(5);
        map.put("auth_token", flowProperties.getAuthToken());
        map.put("trans_id", String.valueOf(System.currentTimeMillis()));
        if(CollectionUtils.isNotEmpty(monthList)) {
            monthList.forEach(month -> {
                map.put("month_ary", month);
            });
        }
        String result = HttpUtils.get(flowProperties.getRequestUrl().getCdrBrand(), map);
    }

    @Override
    public AbstractFlowDto<FlowDeviceOrderDto> pkgOrder(String imei, String pkgTypeId) {
        Map<String, Object> map = new HashMap<>(5);
        map.put("auth_token", flowProperties.getAuthToken());
        map.put("trans_id", String.valueOf(System.currentTimeMillis()));
        map.put("imei", imei);
        map.put("pkg_type_id", pkgTypeId);
        String result = HttpUtils.post(flowProperties.getRequestUrl().getPkgOrder(), map);
        log.info("send pkg order, result={}", result);
        try {
            AbstractFlowDto<FlowDeviceOrderDto> flowDto = mapper.readValue(result, new TypeReference<AbstractFlowDto<FlowDeviceOrderDto>>() {});
            return flowDto;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean pkgRecord(String imei, String recordId) {
        Map<String, Object> map = new HashMap<>(5);
        map.put("auth_token", flowProperties.getAuthToken());
        map.put("trans_id", String.valueOf(System.currentTimeMillis()));
        map.put("imei", imei);
        map.put("record_id", recordId);
        map.put("action", "invalidate");
        String result = HttpUtils.post(flowProperties.getRequestUrl().getPkgRecord(), map);
        log.info("send pkg record, result={}", result);
        try {
            AbstractFlowDto<FlowDeviceDto> flowDto = mapper.readValue(result, new TypeReference<AbstractFlowDto<FlowDeviceDto>>() {});
            if(!flowDto.getCode().equals(FLOW_SUCCESS)) {
                return false;
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
