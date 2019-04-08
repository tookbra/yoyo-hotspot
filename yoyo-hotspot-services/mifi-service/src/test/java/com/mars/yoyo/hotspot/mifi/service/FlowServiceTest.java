package com.mars.yoyo.hotspot.mifi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mars.yoyo.hotspot.mifi.BaseTest;
import com.mars.yoyo.hotspot.mifi.pojo.DeviceFlow;
import com.mars.yoyo.hotspot.mifi.utils.HttpUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tookbra
 * @date 2018/9/10
 * @description
 */
public class FlowServiceTest extends BaseTest {

    @Autowired
    FlowService flowService;

    @Test
    public void deviceUpdateTest() {
        DeviceFlow deviceFlow = new DeviceFlow();
        deviceFlow.setImei("864319038825278");
        deviceFlow.setWifiPwd("12345678");
        flowService.deviceUpdate(deviceFlow);
    }

    @Test
    public void cdrDeviceTest() {
        flowService.cdrDevice("864319038825278", null);
    }

    @Test
    public void cdrDeviceMonthTest() {
        List<String> list = new ArrayList<>();
        list.add("201801");
        list.add("201802");
        list.add("201803");
        flowService.cdrDevice("864319038825278", list);
    }

    @Test
    public void cdrBrandTest() {
        List<String> list = new ArrayList<>();
        list.add("201801");
        list.add("201802");
        list.add("201803");
        flowService.cdrBrand( list);
    }

    @Test
    public void pkgOrderTest() {
        flowService.pkgOrder("864319038825278", "2");
    }

    @Test
    public void pkgRecordTest() {
       flowService.pkgRecord("864319038825278", "1");
    }
}
