package com.mars.yoyo.hotspot.admin.web.device;

import com.mars.yoyo.hotspot.admin.client.DeviceFeignClient;
import com.mars.yoyo.hotspot.admin.params.ServerInputParameter;
import com.mars.yoyo.hotspot.admin.params.UpgradeInputParameter;
import com.mars.yoyo.hotspot.admin.resutls.base.ResultEx;
import com.mars.yoyo.hotspot.result.RestResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 设备操作接口
 *
 * @author admin
 * @create 2018/6/20
 */
@RestController
@RequestMapping("/operate")
public class DeviceOperateController {

    @Autowired
    private DeviceFeignClient deviceFeignClient;

    /**
     * 查询版本号
     * @param boxId
     * @return
     */
    @PostMapping("/queryVersion")
    public ResultEx queryVersion(String boxId) {
        return deviceFeignClient.queryVersion(boxId);
    }

    /**
     * 设置服务器地址
     * @param parameter
     */
    @PostMapping(value = "/setServer")
    public ResultEx setServer(ServerInputParameter parameter) {
        deviceFeignClient.setServer(parameter);
        return new ResultEx().makeSuccessResult();
    }

    /**
     * 远程重启
     * @param boxId
     */
    @PostMapping(value = "/reboot")
    public ResultEx reboot(String boxId) {
        deviceFeignClient.reboot(boxId);
        return new ResultEx().makeSuccessResult();
    }

    /**
     * 远程更新
     * @param parameter
     */
    @PostMapping(value = "/upgrade")
    public ResultEx upgrade(UpgradeInputParameter parameter) {
        deviceFeignClient.upgrade(parameter);
        return new ResultEx().makeSuccessResult();
    }

    /**
     * 查询iccid
     * @param boxId
     */
    @PostMapping(value = "/queryIccid")
    public ResultEx queryIccid(String boxId) {
        deviceFeignClient.queryIccid(boxId);
        return new ResultEx().makeSuccessResult();
    }

    /**
     * 强制弹出
     * @param boxId
     * @param slot
     */
    @PostMapping(value = "/popUp")
    public ResultEx popBox(String boxId, byte slot) {
        deviceFeignClient.popBox(boxId, slot);
        return new ResultEx().makeSuccessResult();
    }

}
