package com.mars.yoyo.hotspot.admin.web.device;

import com.mars.yoyo.hotspot.admin.params.CommonParameter;
import com.mars.yoyo.hotspot.admin.params.DeviceParameter;
import com.mars.yoyo.hotspot.admin.resutls.DeviceView;
import com.mars.yoyo.hotspot.admin.resutls.base.ListResultEx;
import com.mars.yoyo.hotspot.admin.service.DeviceService;
import com.mars.yoyo.hotspot.result.RestResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

/**
 * 设备接口
 *
 * @author admin
 * @create 2018/5/23
 */
@Controller
@RequestMapping("/device")
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    /**
     * 列表跳转页面
     * @return
     */
    @GetMapping("/list")
    public String list(){
        return "device/list";
    }

    /**
     * 分页查询
     * @return
     */
    @ResponseBody
    @GetMapping("/listDevices")
    public ListResultEx<DeviceView> listDevices(CommonParameter parameter) {
        return deviceService.getDeviceList(parameter);
    }

    /**
     * 列表跳转页面
     * @return
     */
    @GetMapping("/preAdd")
    public String preAdd(){
        return "device/add";
    }

    /**
     * 添加设备
     * @param parameter
     * @return
     */
    @ResponseBody
    @PostMapping("/add")
    public RestResult addDevice(DeviceParameter parameter) {
        return deviceService.addDevice(parameter);
    }

    /**
     * 列表跳转页面
     * @return
     */
    @GetMapping("/preEdit/{deviceId}")
    public String preEdit(ModelMap modelMap, @PathVariable Integer deviceId){
        RestResult result = deviceService.getDeviceById(deviceId);
        if (null == result.getData()) {
            return result.getMsg();
        }
        modelMap.addAttribute("device", result.getData());

        return "device/edit";
    }

    /**
     * 更新设备信息
     * @param parameter
     * @return
     */
    @ResponseBody
    @PostMapping("/update")
    public RestResult updateDevice(DeviceParameter parameter) {
        return deviceService.updateDevice(parameter);
    }

    /**
     * 删除设备信息
     * @param deviceId
     * @return
     */
    @ResponseBody
    @PostMapping("/delete/{deviceId}")
    public RestResult deleteDevice(@PathVariable Integer deviceId) {
        return deviceService.deleteDevice(deviceId);
    }

    /**
     * 获取设备信息
     * @param deviceId
     * @return
     */
    @ResponseBody
    @GetMapping("/getDeviceById")
    public RestResult getDeviceById(Integer deviceId) {
        return deviceService.getDeviceById(deviceId);
    }

}
