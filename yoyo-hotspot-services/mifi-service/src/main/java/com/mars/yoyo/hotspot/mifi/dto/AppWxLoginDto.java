package com.mars.yoyo.hotspot.mifi.dto;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author tookbra
 * @date 2018/9/4
 * @description
 */
@Data
@ToString
public class AppWxLoginDto implements Serializable {

    /**
     * token
     */
    private String token;
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 手机号
     */
    private String phone;
}
