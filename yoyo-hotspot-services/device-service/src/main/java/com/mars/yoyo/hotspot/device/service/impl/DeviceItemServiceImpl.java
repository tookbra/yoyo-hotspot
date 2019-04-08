package com.mars.yoyo.hotspot.device.service.impl;

import com.github.pagehelper.PageHelper;
import com.mars.yoyo.hotspot.device.cache.SessionCache;
import com.mars.yoyo.hotspot.device.dao.DeviceItemMapper;
import com.mars.yoyo.hotspot.device.domain.DeviceItem;
import com.mars.yoyo.hotspot.device.service.DeviceItemService;
import com.mars.yoyo.hotspot.device.session.MifiSession;
import com.mars.yoyo.hotspot.exception.BizFeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author tookbra
 * @date 2018/6/12
 * @description
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class, readOnly = false)
public class DeviceItemServiceImpl implements DeviceItemService {

    @Autowired
    DeviceItemMapper deviceItemMapper;

    @Autowired
    SessionCache sessionCache;

    @Override
    public List<DeviceItem> findByBoxId(String boxId) {
        Example example = new Example(DeviceItem.class);
        example.createCriteria().andEqualTo("deviceId", boxId);
        List<DeviceItem> list = deviceItemMapper.selectByExample(example);
        return list;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<DeviceItem> add(List<DeviceItem> list) {
        deviceItemMapper.insertList(list);
        return list;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(DeviceItem item) {
        deviceItemMapper.insertSelective(item);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(List<DeviceItem> list) {
        deviceItemMapper.batchUpdate(list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(DeviceItem deviceItem) {
        deviceItemMapper.updateByPrimaryKeySelective(deviceItem);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateLeasedByDeviceId(String deviceId) {
        deviceItemMapper.updateLeasedByDeviceId(deviceId);
    }

    @Override
    public DeviceItem findTop1ByBoxId(String boxId) {
//        MifiSession mifiSession = sessionCache.getSession(boxId);
//        if(mifiSession == null) {
//            throw new BizFeignException("设备不在线");
//        }
        PageHelper.startPage(1,1);
        Example example = new Example(DeviceItem.class);
        example.orderBy("level").desc();
        example.createCriteria().andEqualTo("leased", 0).andEqualTo("deviceId", boxId);
        DeviceItem deviceItem = deviceItemMapper.selectOneByExample(example);
        return deviceItem;
    }

    @Override
    public DeviceItem findByPowerId(String deviceId, String powerId) {
        Example example = new Example(DeviceItem.class);
        example.createCriteria().andEqualTo("deviceId", deviceId).andEqualTo("powerBankId", powerId);
        DeviceItem deviceItem = deviceItemMapper.selectOneByExample(example);
        return deviceItem;
    }
}
