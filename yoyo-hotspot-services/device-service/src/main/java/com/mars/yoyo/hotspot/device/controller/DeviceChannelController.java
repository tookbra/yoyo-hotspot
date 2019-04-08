package com.mars.yoyo.hotspot.device.controller;

import com.mars.yoyo.hotspot.device.domain.DeliveryChannel;
import com.mars.yoyo.hotspot.device.domain.Device;
import com.mars.yoyo.hotspot.device.dto.output.DeliveryChannelOutput;
import com.mars.yoyo.hotspot.device.service.DeliveryChannelService;
import com.mars.yoyo.hotspot.device.service.DeviceService;
import com.mars.yoyo.hotspot.result.RestResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tookbra
 * @date 2018/6/15
 * @description
 */
@Slf4j
@RestController
@RequestMapping("/device/channels")
public class DeviceChannelController {

    @Autowired
    DeviceService deviceService;

    @Autowired
    DeliveryChannelService deliveryChannelService;

    /**
     * 查询
     * @param token
     * @return
     */
    @GetMapping("/{token}")
    RestResult<DeliveryChannelOutput> getDevice(@PathVariable String token) {

        Device device = deviceService.findByToken(token);
        if(device == null) {
            return RestResult.error("");
        }
        DeliveryChannel deliveryChannel = deliveryChannelService.findById(device.getDeliveryChannel());
        if(deliveryChannel == null) {
            return RestResult.error("");
        }
        DeliveryChannelOutput deliveryChannelOutput = new DeliveryChannelOutput();
        deliveryChannelOutput.setVideoUrl(deliveryChannel.getStoreVideo());
        deliveryChannelOutput.setImgUrl(deliveryChannel.getStoreImg());
        return RestResult.success(deliveryChannelOutput);
    }
}
