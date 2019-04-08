package com.mars.yoyo.hotspot.mifi.client.fallback;

import com.mars.yoyo.hotspot.mifi.client.DeviceClient;
import com.mars.yoyo.hotspot.mifi.client.SmsClient;
import com.mars.yoyo.hotspot.mifi.dto.input.SmsInputDto;
import com.mars.yoyo.hotspot.mifi.dto.output.DeviceItemOutput;
import com.mars.yoyo.hotspot.result.RestResult;
import lombok.extern.slf4j.Slf4j;

/**
 * @author tookbra
 * @date 2018/5/16
 * @description
 */
@Slf4j
public class DeviceClientFallback implements DeviceClient {


    private void didNotGetResponse() {
        log.error("service '{}' has become unreachable", SmsClient.SERVICE_ID);
    }


    @Override
    public RestResult rent(String deviceId) {
        return null;
    }

    @Override
    public RestResult<DeviceItemOutput> findDeviceItem(String deviceId) {
        return null;
    }
}
