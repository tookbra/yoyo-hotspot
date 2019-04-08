package com.mars.yoyo.hotspot.admin.service.impl;

import com.mars.yoyo.hotspot.admin.dao.DeviceItemMapper;
import com.mars.yoyo.hotspot.admin.dao.DeviceMapper;
import com.mars.yoyo.hotspot.admin.entity.Device;
import com.mars.yoyo.hotspot.admin.entity.DeviceItem;
import com.mars.yoyo.hotspot.admin.enums.DeleteStatusEnum;
import com.mars.yoyo.hotspot.admin.params.CommonParameter;
import com.mars.yoyo.hotspot.admin.params.DeviceItemParameter;
import com.mars.yoyo.hotspot.admin.resutls.DeviceItemView;
import com.mars.yoyo.hotspot.admin.resutls.base.ListResultEx;
import com.mars.yoyo.hotspot.admin.resutls.base.ResultEx;
import com.mars.yoyo.hotspot.admin.service.DeviceItemService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;


/**
 * 设备明细业务实现
 *
 * @author admin
 * @create 2018/5/24
 */
@Slf4j
@Service
public class DeviceItemServiceImpl implements DeviceItemService {

    @Autowired
    private DeviceItemMapper deviceItemMapper;

    @Autowired
    private DeviceMapper deviceMapper;

    @Override
    public ListResultEx<DeviceItemView> getDeviceItemList(CommonParameter parameter) {
        ListResultEx<DeviceItemView> resultEx = new ListResultEx<>();
        // 分页参数
        RowBounds rowBounds = new RowBounds(parameter.getStart(), parameter.getPageSize());
        // 查询参数
        Example example = new Example(DeviceItem.class);
        example.setOrderByClause("id desc");
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("deleted", DeleteStatusEnum.UNDELETE.getCode());
        if (!StringUtils.isEmpty(parameter.getSearchText())) {
            criteria.andLike("powerBankId", "%" + parameter.getSearchText() + "%");
        }
        List<DeviceItem> deviceItems = deviceItemMapper.selectByExampleAndRowBounds(example, rowBounds);
        Integer total = deviceItemMapper.selectCountByExample(example);

        // 设置参数
        List<DeviceItemView> deviceItemViewList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(deviceItems)) {
            DeviceItemView deviceItemView;
            for (DeviceItem deviceItem : deviceItems) {
                deviceItemView = new DeviceItemView();
                BeanUtils.copyProperties(deviceItem, deviceItemView);

                // 设备版本号
                Device params = new Device();
                params.setBoxId(deviceItem.getDeviceId());
                Device device = deviceMapper.selectOne(params);
                if (device != null) {
                    deviceItemView.setVersion(device.getVersion());
                }
                deviceItemViewList.add(deviceItemView);
            }
        }

        resultEx.setDataList(deviceItemViewList);
        resultEx.setTotalCount(total);

        return resultEx.makeSuccessResult();
    }

    @Override
    public ResultEx addDeviceItem(DeviceItemParameter parameter) {
        ResultEx resultEx = new ResultEx();
        if (null == parameter.getDeviceId()) {
            return resultEx.makeFailedResult("参数不能为空");
        }
        if (StringUtils.isEmpty(parameter.getPowerBankId())) {
            return resultEx.makeFailedResult("充电宝ID不能为空");
        }
        DeviceItem deviceItem = new DeviceItem();
        BeanUtils.copyProperties(parameter, deviceItem);

        deviceItemMapper.insertSelective(deviceItem);

        return resultEx.makeSuccessResult();
    }

    @Override
    public ResultEx updateDeviceItem(DeviceItemParameter parameter) {
        ResultEx resultEx = new ResultEx();
        if (null == parameter.getId()) {
            return resultEx.makeFailedResult("参数不能为空");
        }
        DeviceItem deviceItem = new DeviceItem();
        BeanUtils.copyProperties(parameter, deviceItem);

        deviceItemMapper.updateByPrimaryKeySelective(deviceItem);

        return resultEx.makeSuccessResult();
    }

    @Override
    public ResultEx deleteDeviceItem(Integer deviceItemId) {
        ResultEx resultEx = new ResultEx();
        if (null == deviceItemId) {
            return resultEx.makeFailedResult("参数不能为空");
        }
        DeviceItem deviceItem = deviceItemMapper.selectByPrimaryKey(deviceItemId);
        if (null == deviceItem) {
            return resultEx.makeFailedResult("当前设备不存在");
        }
        deviceItem.setDeleted(DeleteStatusEnum.DELETED.getCode());

        deviceItemMapper.updateByPrimaryKeySelective(deviceItem);

        return resultEx.makeSuccessResult();
    }

    @Override
    public ResultEx getDeviceItemById(Integer deviceItemId) {
        ResultEx resultEx = new ResultEx();
        if (null == deviceItemId) {
            return resultEx.makeFailedResult("参数不能为空");
        }
        DeviceItem deviceItem = deviceItemMapper.selectByPrimaryKey(deviceItemId);
        if (null == deviceItem) {
            return resultEx.makeFailedResult("当前设备不存在");
        }
        DeviceItemView deviceItemView = new DeviceItemView();
        BeanUtils.copyProperties(deviceItem, deviceItemView);

        resultEx.setData(deviceItemView);
        return resultEx.makeSuccessResult();
    }

}
