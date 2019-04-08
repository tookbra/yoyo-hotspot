package com.mars.yoyo.hotspot.admin.client;

import com.mars.yoyo.hotspot.admin.params.ServerInputParameter;
import com.mars.yoyo.hotspot.admin.params.UpgradeInputParameter;
import com.mars.yoyo.hotspot.admin.resutls.base.ResultEx;
import com.mars.yoyo.hotspot.result.RestResult;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.cloud.netflix.feign.FeignClientsConfiguration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 设备请求
 *
 * @author admin
 * @create 2018/6/22
 */
@FeignClient(name = DeviceFeignClient.SERVICE_ID, configuration = FeignClientsConfiguration.class)
public interface DeviceFeignClient {

    /**
     * eureka service name
     */
    String SERVICE_ID = "MIFI-DEVICE-SERVER";

    /**
     * 查询版本号
     * @param boxId
     * @return
     */
    @RequestMapping(value = "/device/queryVersion", method = RequestMethod.POST,
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE},
            consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    ResultEx queryVersion(String boxId);

    /**
     * 设置服务器地址
     * @param parameter
     */
    @RequestMapping(value = "/device/setServer", method = RequestMethod.POST,
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE},
            consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    void setServer(ServerInputParameter parameter);

    /**
     * 远程重启
     * @param boxId
     */
    @RequestMapping(value = "/device/reboot", method = RequestMethod.POST,
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE},
            consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    void reboot(String boxId);

    /**
     * 远程更新
     * @param parameter
     */
    @RequestMapping(value = "/device/upgrade", method = RequestMethod.POST,
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE},
            consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    void upgrade(UpgradeInputParameter parameter);

    /**
     * 查询iccid
     * @param boxId
     */
    @RequestMapping(value = "/device/queryIccid", method = RequestMethod.POST,
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE},
            consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    void queryIccid(String boxId);

    /**
     * 强制弹出
     * @param boxId
     * @param slot
     */
    @RequestMapping(value = "/device/popUp", method = RequestMethod.POST,
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE},
            consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    void popBox(@RequestParam("boxId") String boxId, @RequestParam("slot") byte slot);

}
