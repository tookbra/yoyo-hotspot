package com.mars.yoyo.hotspot.security.gate.client;

import com.mars.yoyo.hotspot.result.RestResult;
import com.mars.yoyo.hotspot.security.gate.client.fallback.AuthClientFallback;
import com.mars.yoyo.hotspot.security.gate.dto.output.JWTInfo;
import com.mars.yoyo.hotspot.security.gate.dto.output.UserAuth;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author tookbra
 * @date 2018/5/25
 * @description
 */
@FeignClient(name = AuthClient.SERVICE_ID, fallback = AuthClientFallback.class)
public interface AuthClient {

    /**
     * eureka service name
     */
    String SERVICE_ID = "MIFI-AUTH-SERVER";


    /**
     *
     * @param token
     */
    @RequestMapping(value = "/validate", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE},
            consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    RestResult<UserAuth> validate(@RequestParam("token") String token);
}
