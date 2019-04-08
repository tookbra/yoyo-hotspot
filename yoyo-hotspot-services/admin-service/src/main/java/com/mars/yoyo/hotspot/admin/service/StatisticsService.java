package com.mars.yoyo.hotspot.admin.service;

import com.mars.yoyo.hotspot.admin.params.CommonParameter;
import com.mars.yoyo.hotspot.admin.resutls.StatisticsView;
import com.mars.yoyo.hotspot.admin.resutls.base.ListResultEx;
import com.mars.yoyo.hotspot.result.RestResult;

import java.util.Map;

/**
 * 统计
 *
 * @author admin
 * @create 2018/6/17
 */
public interface StatisticsService {

    /**
     * 根据店家名称，统计设备数据
     * @return
     */
    ListResultEx<StatisticsView> getStatisticsViewList(CommonParameter parameter);

    /**
     * 统计出借次数，总押金
     * @return
     */
    RestResult<Map<String, Object>> getTotalRentAndDeposit();

}
