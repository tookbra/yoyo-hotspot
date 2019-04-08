package com.mars.yoyo.hotspot.mifi.client.fallback;

import com.mars.yoyo.hotspot.mifi.client.AuthClient;
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
public class AuthClientFallback implements AuthClient {

    @Override
    public RestResult wxLogin(String userName, Integer userId) {
        didNotGetResponse();
        return null;
    }

    @Override
    public RestResult wxValidate(String userName, Integer userId, String token) {
        didNotGetResponse();
        return null;
    }

    private void didNotGetResponse() {
        log.error("service '{}' has become unreachable", AuthClient.SERVICE_ID);
    }
}
