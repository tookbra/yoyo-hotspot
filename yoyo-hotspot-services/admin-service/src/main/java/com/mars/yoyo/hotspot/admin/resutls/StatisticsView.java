package com.mars.yoyo.hotspot.admin.resutls;

import lombok.Data;

import java.io.Serializable;

/**
 * 统计参数
 *
 * @author admin
 * @create 2018/6/18
 */
@Data
public class StatisticsView implements Serializable {

    /**
     * 店家名称
     */
    private String storeName;

    /**
     * 设备数量
     */
    private Integer deviceNum;

    /**
     * 模块数量
     */
    private Integer itemNum;

    /**
     * 出借数量
     */
    private Integer rentNum;

    /**
     * 剩余数量
     */
    private Integer remainNum;


}
