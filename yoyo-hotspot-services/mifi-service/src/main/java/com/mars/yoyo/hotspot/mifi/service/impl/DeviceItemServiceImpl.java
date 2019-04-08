package com.mars.yoyo.hotspot.mifi.service.impl;

import com.mars.yoyo.hotspot.mifi.dao.DeviceItemMapper;
import com.mars.yoyo.hotspot.mifi.domain.DeviceItem;
import com.mars.yoyo.hotspot.mifi.service.DeviceItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author tookbra
 * @date 2018/6/21
 * @description
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class, readOnly = true)
public class DeviceItemServiceImpl implements DeviceItemService {

    @Resource
    DeviceItemMapper deviceItemMapper;

    @Override
    public DeviceItem findByPowerBankId(String deviceId, String powerBankId) {
        Example example = new Example(DeviceItem.class);
        example.createCriteria()
                .andEqualTo("deviceId", deviceId)
                .andEqualTo("powerBankId", powerBankId);
        DeviceItem deviceItem = deviceItemMapper.selectOneByExample(example);
        return deviceItem;
    }

    @Override
    public DeviceItem findByPowerBankIdAndSlot(String powerBankId, int slot) {
        Example example = new Example(DeviceItem.class);
        example.createCriteria().andEqualTo("powerBankId", powerBankId).andEqualTo("slot", slot);
        DeviceItem deviceItem = deviceItemMapper.selectOneByExample(example);
        return deviceItem;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DeviceItem save(DeviceItem deviceItem) {
        deviceItemMapper.updateByPrimaryKeySelective(deviceItem);
        return deviceItem;
    }

    @Override
    public List<DeviceItem> findByDeviceId(String deviceId) {
        Example example = new Example(DeviceItem.class);
        example.createCriteria()
                .andEqualTo("deviceId", deviceId)
                .andEqualTo("deleted", 0)
                .andEqualTo("leased", 0);
        List<DeviceItem> list = deviceItemMapper.selectByExample(example);
        return list;


    }
}
