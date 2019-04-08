package com.mars.yoyo.hotspot.admin.service.impl;

import com.mars.yoyo.hotspot.admin.dao.DeviceItemMapper;
import com.mars.yoyo.hotspot.admin.dao.DeviceMapper;
import com.mars.yoyo.hotspot.admin.entity.Device;
import com.mars.yoyo.hotspot.admin.entity.DeviceItem;
import com.mars.yoyo.hotspot.admin.enums.DeleteStatusEnum;
import com.mars.yoyo.hotspot.admin.params.CommonParameter;
import com.mars.yoyo.hotspot.admin.params.DeviceParameter;
import com.mars.yoyo.hotspot.admin.resutls.DeviceView;
import com.mars.yoyo.hotspot.admin.resutls.base.ListResultEx;
import com.mars.yoyo.hotspot.admin.service.DeviceService;
import com.mars.yoyo.hotspot.admin.utils.MD5Utils;
import com.mars.yoyo.hotspot.result.RestResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

/**
 * 设备信息实现类
 *
 * @author admin
 * @create 2018/5/14
 */
@Slf4j
@Service("deviceService")
public class DeviceServiceImpl implements DeviceService {

    @Autowired
    private DeviceMapper deviceMapper;

    @Autowired
    private DeviceItemMapper deviceItemMapper;

    @Override
    public ListResultEx<DeviceView> getDeviceList(CommonParameter parameter) {
        ListResultEx<DeviceView> resultEx = new ListResultEx<>();

        // 设置查询参数
        RowBounds rowBounds = new RowBounds(parameter.getStart(), parameter.getPageSize());
        Example example = new Example(Device.class);
        example.setOrderByClause(" id desc ");
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("deleted", DeleteStatusEnum.UNDELETE.getCode());
        if (StringUtils.isNotBlank(parameter.getSearchText())) {
            criteria.andLike("boxId", "%" + parameter.getSearchText() + "%");
        }
        List<Device> deviceList = deviceMapper.selectByExampleAndRowBounds(example, rowBounds);
        Integer total = deviceMapper.selectCountByExample(example);

        List<DeviceView> deviceViewList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(deviceList)) {
            DeviceView deviceView;
            for (Device device : deviceList) {
                deviceView = new DeviceView();
                BeanUtils.copyProperties(device, deviceView);

                // 查询设备总数
                Integer itemTotal = deviceItemMapper.itemTotalByDeviceId(device.getBoxId());
                deviceView.setTotal(itemTotal);

                deviceViewList.add(deviceView);
            }
        }

        resultEx.setDataList(deviceViewList);
        resultEx.setTotalCount(total);
        return resultEx.makeSuccessResult();
    }

    @Override
    public RestResult addDevice(DeviceParameter parameter) {
        // 参数校验
        if(StringUtils.isBlank(parameter.getBoxId())) {
            return RestResult.error("机柜编号不能为空");
        }
        if (StringUtils.isBlank(parameter.getVersion())) {
            return RestResult.error("机柜版本号不能为空");
        }
        if (StringUtils.isBlank(parameter.getAddress())) {
            return RestResult.error("存放地址不能为空");
        }

        Device device = new Device();
        BeanUtils.copyProperties(parameter, device);
        device.setToken(MD5Utils.digest(parameter.getBoxId()));

        deviceMapper.insertSelective(device);

        return RestResult.success("添加设备成功");
    }

    @Override
    public RestResult updateDevice(DeviceParameter parameter) {
        if (null == parameter.getId()) {
            return RestResult.error("参数不能为空");
        }
        Device device = deviceMapper.selectByPrimaryKey(parameter.getId());
        if (null == device) {
            return RestResult.error("当前设备不存在");
        }
        BeanUtils.copyProperties(parameter, device);

        deviceMapper.updateByPrimaryKeySelective(device);

        return RestResult.success("更新设备成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public RestResult deleteDevice(Integer deviceId) {
        if (null == deviceId) {
            return RestResult.error("参数不能为空");
        }
        Device device = deviceMapper.selectByPrimaryKey(deviceId);
        if (null == device) {
            return RestResult.error("当前设备不存在");
        }
        device.setDeleted(DeleteStatusEnum.DELETED.getCode());

        // 设置wifi设备为删除状态
        Example example = new Example(DeviceItem.class);
        example.createCriteria().andEqualTo("deviceId", deviceId);
        List<DeviceItem> deviceItems = deviceItemMapper.selectByExample(example);
        if (CollectionUtils.isNotEmpty(deviceItems)) {
            for (DeviceItem deviceItem : deviceItems) {
                deviceItem.setDeleted(DeleteStatusEnum.DELETED.getCode());
                deviceItemMapper.updateByPrimaryKeySelective(deviceItem);
            }
        }
        // 设置设备为删除状态
        deviceMapper.updateByPrimaryKeySelective(device);

        return RestResult.success("删除设备成功");
    }

    @Override
    public RestResult getDeviceById(Integer deviceId) {
        if (null == deviceId) {
            return RestResult.error("参数不能为空");
        }
        Device device = deviceMapper.selectByPrimaryKey(deviceId);
        if (null == device) {
            return RestResult.error("当前设备不存在");
        }
        DeviceView deviceView = new DeviceView();
        BeanUtils.copyProperties(device, deviceView);

        return RestResult.success(deviceView);
    }

}
