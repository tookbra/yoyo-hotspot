package com.mars.yoyo.hotspot.device.dao;

import com.mars.yoyo.hotspot.device.BaseTest;
import com.mars.yoyo.hotspot.device.domain.DeviceItem;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author tookbra
 * @date 2018/6/20
 * @description
 */
public class DeviceItemMapperTest extends BaseTest {

    @Resource
    DeviceItemMapper deviceItemMapper;

    @Test
    public void batchUpdateTest() {
        List<DeviceItem> list = new ArrayList<>();
        DeviceItem deviceItem1 = new DeviceItem();
        deviceItem1.setSlot((short)1);
        deviceItem1.setPowerBankId("123");
        deviceItem1.setLevel((short)1);
        deviceItem1.setId(1);
        DeviceItem deviceItem2 = new DeviceItem();
        deviceItem2.setSlot((short)1);
        deviceItem2.setPowerBankId("123");
        deviceItem2.setLevel((short)1);
        deviceItem2.setId(1);
        list.add(deviceItem1);
        list.add(deviceItem2);
        deviceItemMapper.batchUpdate(list);
    }

    @Test
    public void updateLeasedByDeviceIdTest() {
        deviceItemMapper.updateLeasedByDeviceId("test");
    }

}
