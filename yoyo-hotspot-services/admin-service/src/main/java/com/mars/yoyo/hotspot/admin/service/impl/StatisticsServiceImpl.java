package com.mars.yoyo.hotspot.admin.service.impl;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.mars.yoyo.hotspot.admin.dao.DeliveryChannelMapper;
import com.mars.yoyo.hotspot.admin.dao.DeviceItemMapper;
import com.mars.yoyo.hotspot.admin.dao.DeviceMapper;
import com.mars.yoyo.hotspot.admin.dao.PayOrderMapper;
import com.mars.yoyo.hotspot.admin.entity.DeliveryChannel;
import com.mars.yoyo.hotspot.admin.entity.Device;
import com.mars.yoyo.hotspot.admin.entity.DeviceItem;
import com.mars.yoyo.hotspot.admin.entity.PayOrder;
import com.mars.yoyo.hotspot.admin.enums.OrderTypeEnum;
import com.mars.yoyo.hotspot.admin.params.CommonParameter;
import com.mars.yoyo.hotspot.admin.resutls.StatisticsView;
import com.mars.yoyo.hotspot.admin.resutls.base.ListResultEx;
import com.mars.yoyo.hotspot.admin.service.StatisticsService;
import com.mars.yoyo.hotspot.result.RestResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 统计接口实现
 *
 * @author admin
 * @create 2018/6/19
 */
@Slf4j
@Service
public class StatisticsServiceImpl implements StatisticsService {

    @Autowired
    private DeliveryChannelMapper deliveryChannelMapper;

    @Autowired
    private DeviceMapper deviceMapper;

    @Autowired
    private DeviceItemMapper deviceItemMapper;

    @Autowired
    private PayOrderMapper payOrderMapper;

    @Override
    public ListResultEx<StatisticsView> getStatisticsViewList(CommonParameter parameter) {
        ListResultEx<StatisticsView> resultEx = new ListResultEx<>();

        RowBounds rowBounds = new RowBounds(parameter.getStart(), parameter.getPageSize());
        // 查询参数
        Example example = new Example(DeliveryChannel.class);
        if (StringUtils.isNotBlank(parameter.getSearchText())) {
            example.createCriteria().andLike("storeName", "%" + parameter.getSearchText() + "%");
        }
        List<DeliveryChannel> channels = deliveryChannelMapper.selectByExampleAndRowBounds(example, rowBounds);
        if (CollectionUtils.isEmpty(channels)) {
            return resultEx.makeFailedResult();
        }
        Integer total = deliveryChannelMapper.selectCountByExample(example);

        // 查询渠道统计参数
        List<StatisticsView> statisticsViews = new ArrayList<>();
        StatisticsView view;
        for (DeliveryChannel channel : channels) {
            view = new StatisticsView();

            // 查询渠道设备总数
            Example deviceEx = new Example(Device.class);
            deviceEx.createCriteria().andEqualTo("deliveryChannel", channel.getId());
            Integer deviceNum = deviceMapper.selectCountByExample(deviceEx);

            // 查询剩余数量
            Integer remainNum = 0;
            List<Device> devices = deviceMapper.selectByExample(deviceEx);
            for (Device device : devices) {
                remainNum += device.getRemainNum();
            }

            // 查询出借数量
            Integer rentNum = this.getLeasedNum(devices);

            // 计算模块总数
            Integer itemNum = remainNum + rentNum;

            // 设置参数
            view.setStoreName(channel.getStoreName());
            view.setDeviceNum(deviceNum);
            view.setItemNum(itemNum);
            view.setRemainNum(remainNum);
            view.setRemainNum(remainNum);

            statisticsViews.add(view);
        }

        resultEx.setDataList(statisticsViews);
        resultEx.setTotalCount(total);

        return resultEx.makeSuccessResult();
    }

    /**
     * 出借数量
     * @param devices
     * @return
     */
    private Integer getLeasedNum(List<Device> devices) {
        Integer rentNum = 0;
        if (CollectionUtils.isEmpty(devices)) {
            return rentNum;
        }
        // 抽取boxId
        List<String> boxIds = Lists.transform(devices, new Function<Device, String>() {
            @Override
            public String apply(@NullableDecl Device device) {
                return String.valueOf(device.getBoxId());
            }
        });

        // 查询出现总数
        Example rentEx = new Example(DeviceItem.class);
        rentEx.createCriteria().andIn("deviceId", boxIds).andEqualTo("leased", 1);
        rentNum = deviceItemMapper.selectCountByExample(rentEx);
        return rentNum;
    }


    @Override
    public RestResult<Map<String, Object>> getTotalRentAndDeposit() {
        Map<String, Object> result = new HashMap<>();
        // 总出借次数查询
        Example rentEx = new Example(PayOrder.class);
        rentEx.createCriteria().andEqualTo("orderType", OrderTypeEnum.RENT.getCode());
        Integer rentNum = payOrderMapper.selectCountByExample(rentEx);
        result.put("rentNum", rentNum);

        // 总押金查询
        Example pledgeEx = new Example(PayOrder.class);
        pledgeEx.createCriteria().andEqualTo("orderType", OrderTypeEnum.DEPOSIT.getCode());
        List<PayOrder> orders = payOrderMapper.selectByExample(pledgeEx);
        if (CollectionUtils.isNotEmpty(orders)) {
            BigDecimal deposit = new BigDecimal(0.00);
            for (PayOrder order : orders) {
                deposit.add(order.getAmount());
            }
            result.put("deposit", deposit);
        }

        return RestResult.success(result);
    }

}
