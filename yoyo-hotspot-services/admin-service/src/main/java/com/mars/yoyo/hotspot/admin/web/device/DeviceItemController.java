package com.mars.yoyo.hotspot.admin.web.device;

import com.mars.yoyo.hotspot.admin.params.CommonParameter;
import com.mars.yoyo.hotspot.admin.params.DeviceItemParameter;
import com.mars.yoyo.hotspot.admin.resutls.DeviceItemView;
import com.mars.yoyo.hotspot.admin.resutls.base.ListResultEx;
import com.mars.yoyo.hotspot.admin.resutls.base.ResultEx;
import com.mars.yoyo.hotspot.admin.service.DeviceItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 设备明细
 *
 * @author admin
 * @create 2018/5/23
 */
@Controller
@RequestMapping("/item")
public class DeviceItemController {

    @Autowired
    private DeviceItemService deviceItemService;

    /**
     * 列表跳转页面
     * @return
     */
    @GetMapping("/list")
    public String list(){
        return "device/item/list";
    }

    /**
     * 分页查询
     * @return
     */
    @ResponseBody
    @GetMapping("/listDeviceItems")
    public ListResultEx<DeviceItemView> listDeviceItems(CommonParameter parameter) {
        return deviceItemService.getDeviceItemList(parameter);
    }

    /**
     * 添加设备
     * @param parameter
     * @return
     */
    @PostMapping("/add")
    public ResultEx addDeviceItem(DeviceItemParameter parameter) {
        return deviceItemService.addDeviceItem(parameter);
    }

    /**
     * 更新设备信息
     * @param parameter
     * @return
     */
    @PostMapping("/update")
    public ResultEx updateDeviceItem(DeviceItemParameter parameter) {
        return deviceItemService.updateDeviceItem(parameter);
    }

    /**
     * 删除设备信息
     * @param deviceItemId
     * @return
     */
    @PostMapping("/delete")
    public ResultEx deleteDeviceItem(Integer deviceItemId) {
        return deviceItemService.deleteDeviceItem(deviceItemId);
    }

    /**
     * 获取设备信息
     * @param deviceItemId
     * @return
     */
    @GetMapping("/getDeviceItemById")
    public ResultEx getDeviceItemById(Integer deviceItemId) {
        return deviceItemService.getDeviceItemById(deviceItemId);
    }

}
