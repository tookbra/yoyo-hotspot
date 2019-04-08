package com.mars.yoyo.hotspot.mifi.client;

import com.mars.yoyo.hotspot.common.dps.feign.FeignClientConfiguration;
import com.mars.yoyo.hotspot.mifi.client.fallback.SmsClientFallback;
import com.mars.yoyo.hotspot.mifi.dto.input.SmsInputDto;
import com.mars.yoyo.hotspot.mifi.dto.output.DeviceItemOutput;
import com.mars.yoyo.hotspot.result.RestResult;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * @author tookbra
 * @date 2018/6/14
 * @description
 */
@FeignClient(name = DeviceClient.SERVICE_ID, configuration = FeignClientConfiguration.class, fallback = SmsClientFallback.class)
public interface DeviceClient {

    /**
     * eureka service name
     */
    String SERVICE_ID = "MIFI-DEVICE-SERVER";

    /**
     * 租借设备
     * @param deviceId 设备id
     */
    @RequestMapping(value = "/device/rent", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE},
            consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    RestResult rent(@RequestParam("deviceId") String deviceId);

    /**
     * 查询是否还有可用设备
     * @param deviceId 设备id
     * @return
     */
    @RequestMapping(value = "/device/findDeviceItem", method = RequestMethod.POST)
    RestResult<DeviceItemOutput> findDeviceItem(@RequestParam("deviceId") String deviceId);
}
