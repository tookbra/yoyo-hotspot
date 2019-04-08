package com.mars.yoyo.hotspot.mifi.dto.output;

import lombok.Data;

/**
 * @author tookbra
 * @date 2018/6/6
 * @description
 */
@Data
public class UserOrderStatusDto {

    /**
     * 状态码
     */
    private int code;
    /**
     * 套餐名称
     */
    private String productName;
}
