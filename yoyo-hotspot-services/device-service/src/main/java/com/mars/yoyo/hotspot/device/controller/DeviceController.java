package com.mars.yoyo.hotspot.device.controller;

import com.mars.yoyo.hotspot.device.domain.Device;
import com.mars.yoyo.hotspot.device.domain.DeviceItem;
import com.mars.yoyo.hotspot.device.dto.DeviceDto;
import com.mars.yoyo.hotspot.device.dto.input.ServerInput;
import com.mars.yoyo.hotspot.device.dto.input.UpgradeInput;
import com.mars.yoyo.hotspot.device.dto.output.DeviceItemOutput;
import com.mars.yoyo.hotspot.device.service.BoxService;
import com.mars.yoyo.hotspot.device.service.DeviceItemService;
import com.mars.yoyo.hotspot.device.service.DeviceService;
import com.mars.yoyo.hotspot.result.RestResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tookbra
 * @date 2018/6/12
 * @description
 */
@Slf4j
@RestController
@RequestMapping("/device")
public class DeviceController {

    @Autowired
    BoxService boxService;

    @Autowired
    DeviceItemService deviceItemService;

    @Autowired
    DeviceService deviceService;


    @Autowired
    MessageSource messageSource;


    /**
     * 查询版本号
     * @param boxId
     */
    @PostMapping("/queryVersion")
    void queryVersion(String boxId) {
        boxService.queryVersion(boxId);
    }

    /**
     * 设置服务器地址
     * @param serverInput
     */
    @PostMapping("/setServer")
    void setServer(ServerInput serverInput) {
        boxService.setServer(serverInput);
    }

    /**
     * 查询库存
     * @param boxId
     */
    @PostMapping("/queryInventory")
    void queryInventory(String boxId) {
        boxService.queryInventory(boxId);
    }

    /**
     * 远程重启
     * @param boxId
     */
    @PostMapping("/reboot")
    void reboot(String boxId) {
        boxService.reboot(boxId);
    }

    /**
     * 远程更新
     * @param upgradeInput
     */
    @PostMapping("/upgrade")
    void upgrade(UpgradeInput upgradeInput) {
        boxService.upgrade(upgradeInput);
    }

    /**
     * 查询iccid
     * @param boxId
     */
    @PostMapping("/queryIccid")
    void queryIccid(String boxId) {
        boxService.queryIccid(boxId);
    }

    /**
     * 强制弹出
     * @param boxId
     * @param slot
     */
    @PostMapping("popUp")
    void popBox(String boxId, byte slot) {
        boxService.popUp(boxId, slot);
    }

    @GetMapping("/rent")
    RestResult rent(@RequestParam String deviceId) {
        boxService.rent(deviceId);
        return RestResult.success("");
    }

    @PostMapping("/findDeviceItem")
    RestResult<DeviceItemOutput> findDeviceItem(String deviceId) {
        DeviceItem deviceItem = deviceItemService.findTop1ByBoxId(deviceId);
        if(deviceItem == null) {
            return RestResult.error("");
        }
        DeviceItemOutput deviceItemOutput = new DeviceItemOutput();
        deviceItemOutput.setPowerBankId(deviceItem.getPowerBankId());
        deviceItemOutput.setDeviceId(deviceItem.getDeviceId());
        deviceItemOutput.setSlot(deviceItem.getSlot());
        return RestResult.success(deviceItemOutput);
    }

    /**
     * 获取附近的设备
     * @param lng
     * @param lat
     * @param radius
     * @return
     */
    @PostMapping
    RestResult<List<DeviceDto>> findNearDeviceByPage(Double lng, Double lat, Integer radius) {
        List<DeviceDto> deviceDtoList = new ArrayList<>();
        List<Device> list = deviceService.findNearDeviceByPage(lng, lat, radius);
        list.forEach(t -> {
            DeviceDto deviceDto = new DeviceDto();
            deviceDto.setLat(t.getLatitude());
            deviceDto.setLng(t.getLongitude());
            deviceDto.setBoxId(t.getToken());
            deviceDto.setStoreName(t.getAddress());
            deviceDto.setAddress(t.getAddressDetail());
            deviceDtoList.add(deviceDto);
        });
        return RestResult.success(deviceDtoList);
    }

    /**
     * 检查设备是否可用
     * @param token
     * @return
     */
    @GetMapping("/{token}")
    RestResult getState(@PathVariable String token) {
        return RestResult.success(deviceService.checkState(token));
    }
}
