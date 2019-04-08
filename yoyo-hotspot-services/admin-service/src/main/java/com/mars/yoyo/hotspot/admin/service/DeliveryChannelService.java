package com.mars.yoyo.hotspot.admin.service;

import com.mars.yoyo.hotspot.admin.params.ChannelParameter;
import com.mars.yoyo.hotspot.admin.params.CommonParameter;
import com.mars.yoyo.hotspot.admin.resutls.ChannelView;
import com.mars.yoyo.hotspot.admin.resutls.base.ListResultEx;
import com.mars.yoyo.hotspot.admin.resutls.base.ResultEx;
import com.mars.yoyo.hotspot.result.RestResult;

/**
 * 设备投放渠道管理接口
 *
 * @author admin
 * @create 2018/6/5
 */
public interface DeliveryChannelService {

    /**
     * 分页查询
     * @return
     */
    ListResultEx<ChannelView> listChannels(CommonParameter parameter);

    /**
     * 添加
     * @param parameter
     * @return
     */
    RestResult addChannel(ChannelParameter parameter);

    /**
     * 更新
     * @param parameter
     * @return
     */
    RestResult updateChannel(ChannelParameter parameter);

    /**
     * 删除信息
     * @param channelId
     * @return
     */
    RestResult deleteChannel(Integer channelId);

    /**
     * 获取信息
     * @param channelId
     * @return
     */
    RestResult getChannelById(Integer channelId);


}
