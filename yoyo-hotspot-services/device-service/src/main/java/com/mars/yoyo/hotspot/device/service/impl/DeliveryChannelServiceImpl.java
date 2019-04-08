package com.mars.yoyo.hotspot.device.service.impl;

import com.mars.yoyo.hotspot.device.dao.DeliveryChannelMapper;
import com.mars.yoyo.hotspot.device.domain.DeliveryChannel;
import com.mars.yoyo.hotspot.device.service.DeliveryChannelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author tookbra
 * @date 2018/6/15
 * @description
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class, readOnly = true)
public class DeliveryChannelServiceImpl implements DeliveryChannelService {

    @Resource
    DeliveryChannelMapper deliveryChannelMapper;

    @Override
    public DeliveryChannel findById(int id) {
        DeliveryChannel deliveryChannel = deliveryChannelMapper.selectByPrimaryKey(id);
        return deliveryChannel;
    }
}
