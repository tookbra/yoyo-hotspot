package com.mars.yoyo.hotspot.device.service.impl;

import com.mars.yoyo.hotspot.device.constant.RedisConstant;
import com.mars.yoyo.hotspot.device.dao.DeviceMapper;
import com.mars.yoyo.hotspot.device.domain.Device;
import com.mars.yoyo.hotspot.device.service.DeviceService;
import com.mars.yoyo.hotspot.exception.BizFeignException;
import com.mars.yoyo.hotspot.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author tookbra
 * @date 2018/6/11
 * @description
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class, readOnly = true)
public class DeviceServiceImpl implements DeviceService {

    @Autowired
    DeviceMapper deviceMapper;

    @Override
//    @Cacheable(cacheNames = RedisConstant.DEVICE, key = "#p0", cacheManager = "daysCacheManager")
    public Device findByBoxId(String boxId) {
        Example example = new Example(Device.class);
        example.createCriteria().andEqualTo("boxId", boxId).andEqualTo("deleted", 0);
        Device device = deviceMapper.selectOneByExample(example);
        return device;
    }

    @Override
    public Device findByToken(String token) {
        Example example = new Example(Device.class);
        example.createCriteria().andEqualTo("token", token).andEqualTo("deleted", 0);
        Device device = deviceMapper.selectOneByExample(example);
        return device;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
//    @CachePut(cacheNames = RedisConstant.DEVICE, key = "#p0.boxId", cacheManager = "daysCacheManager")
    public Device save(Device device) {
        deviceMapper.updateByPrimaryKeySelective(device);
        return device;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
//    @CachePut(cacheNames = RedisConstant.DEVICE, key = "#p0.boxId", cacheManager = "daysCacheManager")
    public Device add(Device device) {
        deviceMapper.insertSelective(device);
        return device;
    }

    @Override
    public List<Device> findNearDeviceByPage(Double lng, Double lat, Integer radius) {
        List<Device> list = deviceMapper.findNearDeviceByPage(lng, lat, radius);
        return list;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateHearTime(String deviceId) {
        Device device = this.findByBoxId(deviceId);
        if(device != null) {
            device.setLastHeart(DateTime.now().toDate());
            this.save(device);
        }
    }

    @Override
    public boolean checkState(String token) {
        Example example = new Example(Device.class);
        example.createCriteria().andEqualTo("token", token).andEqualTo("deleted", 0);
        Device device = deviceMapper.selectOneByExample(example);
        if(device == null) {
            return false;
        }

        int min = DateUtil.getIntervalMin(device.getLastHeart(), DateTime.now().toDate());
        if (min > 5) {
            return false;
        }
        return true;
    }
}
