package com.mars.yoyo.hotspot.admin.web.statistics;

import com.mars.yoyo.hotspot.admin.params.CommonParameter;
import com.mars.yoyo.hotspot.admin.resutls.StatisticsView;
import com.mars.yoyo.hotspot.admin.resutls.base.ListResultEx;
import com.mars.yoyo.hotspot.admin.service.StatisticsService;
import com.mars.yoyo.hotspot.result.RestResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * 统计接口
 *
 * @author admin
 * @create 2018/6/20
 */
@Controller
@RequestMapping("/statistics")
public class StatisticsController {

    @Autowired
    private StatisticsService statisticsService;

    /**
     * 列表跳转页面
     * @return
     */
    @GetMapping("/list")
    public String list(){
        return "statistics/list";
    }

    /**
     * 设备统计
     * @return
     */
    @ResponseBody
    @GetMapping(value = "/listStatistics")
    public ListResultEx<StatisticsView> listStatistics(CommonParameter parameter) {
        return statisticsService.getStatisticsViewList(parameter);
    }

    /**
     * 统计出借次数，总押金
     * @return
     */
    @GetMapping(value = "/getTotalRentAndDeposit")
    public RestResult<Map<String,Object>> getTotalRentAndDeposit() {
        return statisticsService.getTotalRentAndDeposit();
    }

}
