package com.mars.yoyo.hotspot.admin.params;

import lombok.Data;

/**
 * 用户参数
 *
 * @author admin
 * @create 2018/5/12
 */
@Data
public class UserParameter extends PageParameter {

    private Integer id;

    /**
     * 关键字搜索
     */
    private String searchText;

    /**
     * 是否禁用状态
     */
    private Integer state;

    /**
     * 起始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;

}
