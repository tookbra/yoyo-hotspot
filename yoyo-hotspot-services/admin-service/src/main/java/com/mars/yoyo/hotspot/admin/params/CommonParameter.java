package com.mars.yoyo.hotspot.admin.params;

import lombok.Data;

/**
 * 公共请求参数
 *
 * @author admin
 * @create 2018/9/1
 */
@Data
public class CommonParameter extends PageParameter {

    /**
     * 查询参数
     */
    private String searchText;

    /**
     * 起始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;


}
