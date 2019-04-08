package com.mars.yoyo.hotspot.auth.client;

import com.mars.yoyo.hotspot.auth.client.fallback.UserClientFallback;
import com.mars.yoyo.hotspot.auth.dto.input.LoginInputDto;
import com.mars.yoyo.hotspot.auth.dto.output.UserOutputDto;
import com.mars.yoyo.hotspot.common.dps.feign.FeignClientConfiguration;
import com.mars.yoyo.hotspot.result.RestResult;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author tookbra
 * @date 2018/5/24
 * @description
 */
@FeignClient(name = UserClient.SERVICE_ID, configuration = FeignClientConfiguration.class, fallback = UserClientFallback.class)
public interface UserClient {

    /**
     * eureka service name
     */
    String SERVICE_ID = "MIFI-SERVER";


    /**
     *
     * @param request
     */
    @RequestMapping(value = "/users/validate", method = RequestMethod.POST,
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE},
            consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    RestResult<UserOutputDto> validate(@RequestBody LoginInputDto request);

    /**
     *
     * @param request
     */
    @RequestMapping(value = "/users/login", method = RequestMethod.POST,
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE},
            consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    RestResult<UserOutputDto> login(@RequestBody LoginInputDto request);
}
