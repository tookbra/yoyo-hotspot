package com.mars.yoyo.hotspot.security.gate.client.fallback;

import com.mars.yoyo.hotspot.result.RestResult;
import com.mars.yoyo.hotspot.security.gate.client.AuthClient;
import com.mars.yoyo.hotspot.security.gate.dto.output.JWTInfo;
import com.mars.yoyo.hotspot.security.gate.dto.output.UserAuth;
import lombok.extern.slf4j.Slf4j;

/**
 * @author tookbra
 * @date 2018/5/25
 * @description
 */
@Slf4j
public class AuthClientFallback implements AuthClient {
    @Override
    public RestResult<UserAuth> validate(String token) {
        didNotGetResponse();
        return null;
    }

    private void didNotGetResponse() {
        log.error("service '{}' has become unreachable", AuthClient.SERVICE_ID);
    }
}
