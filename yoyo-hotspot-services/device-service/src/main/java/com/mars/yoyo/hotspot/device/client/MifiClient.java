package com.mars.yoyo.hotspot.device.client;

import com.mars.yoyo.hotspot.common.dps.feign.FeignClientConfiguration;
import com.mars.yoyo.hotspot.device.client.fallback.MifiClientFallback;
import com.mars.yoyo.hotspot.device.dto.input.RentReportInput;
import com.mars.yoyo.hotspot.result.RestResult;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author tookbra
 * @date 2018/6/14
 * @description
 */
@FeignClient(name = MifiClient.SERVICE_ID, configuration = FeignClientConfiguration.class, fallback = MifiClientFallback.class)
public interface MifiClient {

    /**
     * eureka service name
     */
    String SERVICE_ID = "MIFI-SERVER";

    /**
     *
     * @param rentReportInput
     * @return
     */
    @RequestMapping(value = "/lease/report", method = RequestMethod.POST,
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE},
            consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    RestResult rentReport(@RequestBody RentReportInput rentReportInput);

    /**
     * 归还设备
     * @param deviceId 设备id
     * @param powerBankId 充电宝id
     * @return
     */
    @RequestMapping(value = "/lease/return", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE},
            consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    RestResult<Boolean> returnReport(@RequestParam("deviceId") String deviceId,
                            @RequestParam("powerBankId") String powerBankId);

    /**
     * 强制弹出设备
     * @param deviceId 设备id
     * @param powerBankId 充电宝id
     * @return
     */
    @RequestMapping(value = "/lease/popReport", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE},
            consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    RestResult<Boolean> popUpReport(@RequestParam("deviceId") String deviceId,
                                    @RequestParam("powerBankId") String powerBankId);
}
