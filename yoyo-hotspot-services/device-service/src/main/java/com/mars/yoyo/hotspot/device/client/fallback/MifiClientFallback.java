package com.mars.yoyo.hotspot.device.client.fallback;

import com.mars.yoyo.hotspot.device.client.MifiClient;
import com.mars.yoyo.hotspot.device.dto.input.RentReportInput;
import com.mars.yoyo.hotspot.result.RestResult;
import lombok.extern.slf4j.Slf4j;

/**
 * @author tookbra
 * @date 2018/6/14
 * @description
 */
@Slf4j
public class MifiClientFallback implements MifiClient {

    private void didNotGetResponse() {
        log.error("service '{}' has become unreachable", MifiClient.SERVICE_ID);
    }

    @Override
    public RestResult rentReport(RentReportInput rentReportInput) {
        return null;
    }

    @Override
    public RestResult<Boolean> returnReport(String deviceId, String powerBankId) {
        return null;
    }

    @Override
    public RestResult<Boolean> popUpReport(String deviceId, String powerBankId) {
        return null;
    }
}
