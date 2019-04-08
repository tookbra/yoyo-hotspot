package com.mars.yoyo.hotspot.admin.service;

import com.mars.yoyo.hotspot.admin.params.CommonParameter;
import com.mars.yoyo.hotspot.admin.params.DeviceParameter;
import com.mars.yoyo.hotspot.admin.resutls.DeviceView;
import com.mars.yoyo.hotspot.admin.resutls.base.ListResultEx;
import com.mars.yoyo.hotspot.result.RestResult;

/**
 * 设备信息
 *
 * @author admin
 * @create 2018/5/10
 */
public interface DeviceService {

    /**
     * 分页查询
     * @return
     */
    ListResultEx<DeviceView> getDeviceList(CommonParameter parameter);

    /**
     * 添加设备
     * @param parameter
     * @return
     */
    RestResult addDevice(DeviceParameter parameter);

    /**
     * 更新设备信息
     * @param parameter
     * @return
     */
    RestResult updateDevice(DeviceParameter parameter);

    /**
     * 删除设备信息
     * @param deviceId
     * @return
     */
    RestResult deleteDevice(Integer deviceId);

    /**
     * 获取设备信息
     * @param deviceId
     * @return
     */
    RestResult<DeviceView> getDeviceById(Integer deviceId);

}
