package com.mars.yoyo.hotspot.admin.params;

import lombok.Data;

/**
 * 设备投放渠道参数
 *
 * @author admin
 * @create 2018/6/5
 */
@Data
public class ChannelParameter extends SessionParameter {

    private Integer id;

    /**
     * 渠道名称
     */
    private String name;

    /**
     * 渠道码
     */
    private String token;

    /**
     * 店家名称
     */
    private String storeName;

    /**
     * 店家视频地址
     */
    private String storeVideo;

    /**
     * 是否有活动 1是0否
     */
    private Integer activityed;

    /**
     * 是否有押金
     */
    private Integer deposit;

}
