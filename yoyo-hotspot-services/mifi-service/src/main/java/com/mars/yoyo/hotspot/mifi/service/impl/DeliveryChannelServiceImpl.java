package com.mars.yoyo.hotspot.mifi.service.impl;

import com.mars.yoyo.hotspot.mifi.dao.DeliveryChannelMapper;
import com.mars.yoyo.hotspot.mifi.domain.DeliveryChannel;
import com.mars.yoyo.hotspot.mifi.service.DeliveryChannelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author tookbra
 * @date 2018/6/3
 * @description
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class, readOnly = true)
public class DeliveryChannelServiceImpl implements DeliveryChannelService {

    @Resource
    DeliveryChannelMapper deliveryChannelMapper;

    @Override
    public List<DeliveryChannel> findAll() {
        List<DeliveryChannel> list = deliveryChannelMapper.selectAll();
        return list;
    }

    @Override
    public DeliveryChannel findById(int id) {
        DeliveryChannel deliveryChannel = deliveryChannelMapper.selectByPrimaryKey(id);
        return deliveryChannel;
    }
}
