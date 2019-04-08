package com.mars.yoyo.hotspot.admin.resutls;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 设备渠道参数
 */
@Data
public class ChannelView implements Serializable {

    private static final long serialVersionUID = -2548139605037136914L;

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
     * 英文名称
     */
    private String storeNameEn;

    /**
     * 店家视频地址
     */
    private String storeVideo;

    /**
     * 店家视频封面
     */
    private String storeImg;

    /**
     * 是否有押金
     */
    private Integer deposit;

    /**
     * 是否有活动 1是0否
     */
    private Integer activityed;

    /**
     * 创建时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 更新时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date modifyTime;
}