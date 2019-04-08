package com.mars.yoyo.hotspot.mifi.vo;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @author tookbra
 * @date 2018/7/13
 * @description
 */
@Data
@ToString
public class LeaseRecordVo implements Serializable {

    /**
     * 当前使用详情
     */
    private LeaseDetailVo currentLease;
    /**
     * 历史使用详情
     */
    private List<LeaseDetailVo> oldList;
}
