package com.mars.yoyo.hotspot.admin.service;

import com.mars.yoyo.hotspot.admin.params.CommonParameter;
import com.mars.yoyo.hotspot.admin.params.DeviceItemParameter;
import com.mars.yoyo.hotspot.admin.resutls.DeviceItemView;
import com.mars.yoyo.hotspot.admin.resutls.base.ListResultEx;
import com.mars.yoyo.hotspot.admin.resutls.base.ResultEx;

/**
 * 设备明细
 *
 * @author admin
 * @create 2018/5/14
 * @since 1.0.0
 */
public interface DeviceItemService {

    /**
     * 分页查询
     * @return
     */
    ListResultEx<DeviceItemView> getDeviceItemList(CommonParameter parameter);

    /**
     * 添加设备
     * @param parameter
     * @return
     */
    ResultEx addDeviceItem(DeviceItemParameter parameter);

    /**
     * 更新设备信息
     * @param parameter
     * @return
     */
    ResultEx updateDeviceItem(DeviceItemParameter parameter);

    /**
     * 删除设备信息
     * @param deviceItemId
     * @return
     */
    ResultEx deleteDeviceItem(Integer deviceItemId);

    /**
     * 获取设备信息
     * @param deviceItemId
     * @return
     */
    ResultEx getDeviceItemById(Integer deviceItemId);

}
