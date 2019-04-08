package com.mars.yoyo.hotspot.admin.service.impl;

import com.mars.yoyo.hotspot.admin.dao.DeliveryChannelMapper;
import com.mars.yoyo.hotspot.admin.entity.DeliveryChannel;
import com.mars.yoyo.hotspot.admin.params.ChannelParameter;
import com.mars.yoyo.hotspot.admin.params.CommonParameter;
import com.mars.yoyo.hotspot.admin.resutls.ChannelView;
import com.mars.yoyo.hotspot.admin.resutls.base.ListResultEx;
import com.mars.yoyo.hotspot.admin.resutls.base.ResultEx;
import com.mars.yoyo.hotspot.admin.service.DeliveryChannelService;
import com.mars.yoyo.hotspot.result.RestResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 设备渠道管理接口
 *
 * @author admin
 * @create 2018/6/5
 */
@Slf4j
@Service
public class DeliveryChannelServiceImpl implements DeliveryChannelService {

    @Autowired
    private DeliveryChannelMapper deliveryChannelMapper;

    @Override
    public ListResultEx<ChannelView> listChannels(CommonParameter parameter) {
        ListResultEx<ChannelView> resultEx = new ListResultEx<>();

        RowBounds rowBounds = new RowBounds(parameter.getStart(), parameter.getPageSize());
        // 查询参数
        Example example = new Example(DeliveryChannel.class);
        example.setOrderByClause(" create_time desc");
        if (StringUtils.isNotBlank(parameter.getSearchText())) {
            example.createCriteria().andLike("name", "%" + parameter.getSearchText() + "%")
                                    .orLike("token", "%" + parameter.getSearchText() + "%");
        }
        List<DeliveryChannel> channels = deliveryChannelMapper.selectByExampleAndRowBounds(example, rowBounds);
        Integer total = deliveryChannelMapper.selectCountByExample(example);

        List<ChannelView> channelViewList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(channels)) {
            ChannelView channelView;
            for (DeliveryChannel deliveryChannel : channels) {
                channelView = new ChannelView();
                BeanUtils.copyProperties(deliveryChannel, channelView);
                channelViewList.add(channelView);
            }
        }

        resultEx.setDataList(channelViewList);
        resultEx.setTotalCount(total);
        return resultEx.makeSuccessResult();
    }

    @Override
    public RestResult addChannel(ChannelParameter parameter) {
        if (StringUtils.isBlank(parameter.getName())) {
            return RestResult.error("渠道名称不能为空");
        }
        if (StringUtils.isBlank(parameter.getToken())) {
            return RestResult.error("渠道编码不能为空");
        }

        DeliveryChannel deliveryChannel = new DeliveryChannel();
        BeanUtils.copyProperties(parameter, deliveryChannel);
        Date now = new Date();
        deliveryChannel.setCreateTime(now);
        deliveryChannel.setModifyTime(now);

        deliveryChannelMapper.insertSelective(deliveryChannel);

        return RestResult.success("添加渠道完成");
    }

    @Override
    public RestResult updateChannel(ChannelParameter parameter) {
        if (null == parameter.getId()) {
            return RestResult.error("参数不能为空");
        }
        if (StringUtils.isBlank(parameter.getName())) {
            return RestResult.error("渠道名称不能为空");
        }
        if (StringUtils.isBlank(parameter.getToken())) {
            return RestResult.error("渠道编码不能为空");
        }

        DeliveryChannel deliveryChannel = deliveryChannelMapper.selectByPrimaryKey(parameter.getId());
        if (null == deliveryChannel) {
            return RestResult.error("渠道不存在");
        }
        BeanUtils.copyProperties(parameter, deliveryChannel);
        deliveryChannel.setModifyTime(new Date());
        deliveryChannelMapper.updateByPrimaryKeySelective(deliveryChannel);

        return RestResult.success("修改渠道完成");
    }

    @Override
    public RestResult deleteChannel(Integer channelId) {
        if (null == channelId) {
            return RestResult.error("参数不能为空");
        }
        deliveryChannelMapper.deleteByPrimaryKey(channelId);

        return RestResult.success("删除渠道完成");
    }

    @Override
    public RestResult getChannelById(Integer channelId) {
        if (null == channelId) {
            return RestResult.error("参数不能为空");
        }
        DeliveryChannel deliveryChannel = deliveryChannelMapper.selectByPrimaryKey(channelId);
        if (null == deliveryChannel) {
            return RestResult.error("渠道不存在");
        }

        ChannelView channelView = new ChannelView();
        BeanUtils.copyProperties(deliveryChannel, channelView);

        return RestResult.success(channelView);
    }
}
