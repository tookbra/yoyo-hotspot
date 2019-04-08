package com.mars.yoyo.hotspot.mifi.client;

import com.mars.yoyo.hotspot.common.dps.feign.FeignClientConfiguration;
import com.mars.yoyo.hotspot.mifi.client.fallback.SmsClientFallback;
import com.mars.yoyo.hotspot.mifi.dto.input.SmsInputDto;
import com.mars.yoyo.hotspot.result.RestResult;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * @author tookbra
 * @date 2018/5/16
 * @description
 */
@FeignClient(name = SmsClient.SERVICE_ID, configuration = FeignClientConfiguration.class, fallback = SmsClientFallback.class)
public interface SmsClient {

    /**
     * eureka service name
     */
    String SERVICE_ID = "MIFI-MS-SERVER";


    /**
     * 发送短信
     * @param request
     */
    @RequestMapping(value = "/sms", method = RequestMethod.POST,
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE},
            consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    RestResult sendSms(@RequestBody  SmsInputDto request);
}
