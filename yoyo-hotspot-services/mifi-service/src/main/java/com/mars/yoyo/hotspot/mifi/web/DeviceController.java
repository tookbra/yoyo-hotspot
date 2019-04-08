package com.mars.yoyo.hotspot.mifi.web;

import com.mars.yoyo.hotspot.mifi.service.DeviceService;
import com.mars.yoyo.hotspot.mifi.vo.BannerVo;
import com.mars.yoyo.hotspot.mifi.vo.DeviceVo;
import com.mars.yoyo.hotspot.result.RestResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author tookbra
 * @date 2018/7/21
 * @description
 */
@Slf4j
@RestController
@RequestMapping("/device")
public class DeviceController {

    @Autowired
    DeviceService deviceService;

    @PostMapping
    RestResult<DeviceVo> getDeviceState(String channelId) {
        DeviceVo deviceVo = deviceService.findByChannelId(channelId);
        return RestResult.success(deviceVo);
    }

    /**
     * 查询
     * @param channelId
     * @return
     */
    @GetMapping("/getBanner")
    RestResult<BannerVo> getDevice(@RequestParam(required = false) String channelId) {
        BannerVo bannerVo = deviceService.getBanner(channelId);
        return RestResult.success(bannerVo);
    }
}
