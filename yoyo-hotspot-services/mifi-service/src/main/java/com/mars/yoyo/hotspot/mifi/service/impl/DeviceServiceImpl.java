package com.mars.yoyo.hotspot.mifi.service.impl;

import com.github.pagehelper.PageHelper;
import com.mars.yoyo.hotspot.mifi.dao.BannerMapper;
import com.mars.yoyo.hotspot.mifi.dao.DeliveryChannelMapper;
import com.mars.yoyo.hotspot.mifi.dao.DeviceMapper;
import com.mars.yoyo.hotspot.mifi.domain.Banner;
import com.mars.yoyo.hotspot.mifi.domain.DeliveryChannel;
import com.mars.yoyo.hotspot.mifi.domain.Device;
import com.mars.yoyo.hotspot.mifi.domain.DeviceItem;
import com.mars.yoyo.hotspot.mifi.service.DeviceItemService;
import com.mars.yoyo.hotspot.mifi.service.DeviceService;
import com.mars.yoyo.hotspot.mifi.vo.BannerVo;
import com.mars.yoyo.hotspot.mifi.vo.DeviceVo;
import com.mars.yoyo.hotspot.result.RestResult;
import com.mars.yoyo.hotspot.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author tookbra
 * @date 2018/5/22
 * @description
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class, readOnly = true)
public class DeviceServiceImpl implements DeviceService {

    @Autowired
    DeviceMapper deviceMapper;

    @Autowired
    DeliveryChannelMapper deliveryChannelMapper;

    @Autowired
    DeviceItemService deviceItemService;

    @Autowired
    BannerMapper bannerMapper;

    @Override
    public Device getByToken(String token) {
        Example example = new Example(Device.class);
        example.createCriteria().andEqualTo("boxId", token).andEqualTo("deleted", 0);
        Device device = deviceMapper.selectOneByExample(example);
        return device;
    }

    @Override
    public void findNearDeviceByPage(Double lng, Double lat, Integer radius) {
//        Tuple4<Double> tuple4 = DistanceUtils.calcBoxByDistFromPt(lng, lat, radius);
    }

    @Override
    public DeviceVo findByChannelId(String channelId) {
        DeviceVo deviceVo = new DeviceVo();
        deviceVo.setDeviceOnline(true);
        deviceVo.setDeviceItem(true);
        if(StringUtils.isNotBlank(channelId)) {
            Device device = this.getByToken(channelId);
            if (device == null) {
                log.info("device not found, channelId={}", channelId);
                deviceVo.setDeviceOnline(false);
            }

            int min = DateUtil.getIntervalMin(device.getLastHeart(), DateTime.now().toDate());
            if (min > 5) {
                log.info("device offline, channelId={}", channelId);
                deviceVo.setDeviceOnline(false);
            }

            List<DeviceItem> deviceItemList = deviceItemService.findByDeviceId(device.getBoxId());
            if (CollectionUtils.isEmpty(deviceItemList)) {
                log.info("device has not item, channelId={}", channelId);
                // 无可用设备
                deviceVo.setDeviceItem(false);
            }
            DeliveryChannel deliveryChannel = deliveryChannelMapper.selectByPrimaryKey(device.getDeliveryChannel());
            if(deliveryChannel != null) {
                if(StringUtils.isNotBlank(deliveryChannel.getStoreName())) {
                    deviceVo.setAddress(deliveryChannel.getStoreName());
                }
                if(StringUtils.isNotBlank(deliveryChannel.getStoreNameEn())) {
                    deviceVo.setAddressEn(deliveryChannel.getStoreNameEn());
                }
            }
        } else {
            deviceVo.setDeviceOnline(false);
            deviceVo.setDeviceItem(false);
        }
        return deviceVo;
    }

    @Override
    public BannerVo getBanner(String channelId) {
        BannerVo bannerVo = new BannerVo();
        if (StringUtils.isBlank(channelId)) {
            Example example = new Example(Banner.class);
            PageHelper.startPage(1, 1);
            example.orderBy("createTime").desc();
            Banner banner = bannerMapper.selectOneByExample(example);

            if (banner == null) {
                return null;
            }
            bannerVo.setImgUrl(banner.getImgUrl());
            bannerVo.setVideoUrl(banner.getVideoUrl());
            return bannerVo;
        } else {
            Device device = this.getByToken(channelId);
            if (device == null) {
                return null;
            }

            DeliveryChannel deliveryChannel = deliveryChannelMapper.selectByPrimaryKey(device.getDeliveryChannel());
            if (deliveryChannel == null) {
                return null;
            }
            bannerVo.setVideoUrl(deliveryChannel.getStoreVideo());
            bannerVo.setImgUrl(deliveryChannel.getStoreImg());
            return bannerVo;
        }
    }
}
