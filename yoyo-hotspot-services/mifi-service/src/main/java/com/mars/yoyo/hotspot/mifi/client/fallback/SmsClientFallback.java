package com.mars.yoyo.hotspot.mifi.client.fallback;

import com.mars.yoyo.hotspot.mifi.client.SmsClient;
import com.mars.yoyo.hotspot.mifi.dto.input.SmsInputDto;
import com.mars.yoyo.hotspot.result.RestResult;
import lombok.extern.slf4j.Slf4j;

/**
 * @author tookbra
 * @date 2018/5/16
 * @description
 */
@Slf4j
public class SmsClientFallback implements SmsClient {

    @Override
    public RestResult sendSms(SmsInputDto request) {
        didNotGetResponse();
        return null;
    }

    private void didNotGetResponse() {
        log.error("service '{}' has become unreachable", SmsClient.SERVICE_ID);
    }
}
