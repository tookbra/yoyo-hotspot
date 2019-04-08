package com.mars.yoyo.hotspot.mifi.vo;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author tookbra
 * @date 2018/7/5
 * @description
 */
@Data
@ToString
public class OrderConfirmVo implements Serializable {

    private boolean flag;
    /**
     * 租赁id
     */
    private Integer rentId;
}
