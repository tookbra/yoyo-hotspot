package com.mars.yoyo.hotspot.auth.client.fallback;

import com.mars.yoyo.hotspot.auth.client.UserClient;
import com.mars.yoyo.hotspot.auth.dto.input.LoginInputDto;
import com.mars.yoyo.hotspot.auth.dto.output.UserOutputDto;
import com.mars.yoyo.hotspot.result.RestResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author tookbra
 * @date 2018/5/16
 * @description
 */
@Slf4j
@Component
public class UserClientFallback implements UserClient {

    @Override
    public RestResult<UserOutputDto> validate(LoginInputDto request) {
        didNotGetResponse();
        return null;
    }

    @Override
    public RestResult<UserOutputDto> login(LoginInputDto request) {
        didNotGetResponse();
        return null;
    }

    private void didNotGetResponse() {
        log.error("service '{}' has become unreachable", UserClient.SERVICE_ID);
    }
}
