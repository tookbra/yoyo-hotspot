package com.mars.yoyo.hotspot.mifi.client;

import com.mars.yoyo.hotspot.common.dps.feign.FeignClientConfiguration;
import com.mars.yoyo.hotspot.mifi.client.fallback.AuthClientFallback;
import com.mars.yoyo.hotspot.result.RestResult;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author tookbra
 * @date 2018/8/17
 * @description
 */
@FeignClient(name = AuthClient.SERVICE_ID, configuration = FeignClientConfiguration.class, fallback = AuthClientFallback.class)
public interface AuthClient {

    /**
     * eureka service name
     */
    String SERVICE_ID = "MIFI-AUTH-SERVER";


    /**
     *
     * @param userName
     * @return
     */
    @RequestMapping(value = "/wx/login", method = RequestMethod.POST)
    RestResult wxLogin(@RequestParam("userName") String userName, @RequestParam("id") Integer userId);

    /**
     * 微信 验证token
     * @param userName
     * @param userId
     * @param token
     * @return
     */
    @RequestMapping(value = "/wx/validate", method = RequestMethod.POST)
    RestResult wxValidate(@RequestParam("userName") String userName, @RequestParam("userId") Integer userId,
                          @RequestParam("token") String token);
}
