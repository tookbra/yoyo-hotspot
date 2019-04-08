package com.mars.yoyo.hotspot.mifi.vo;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author tookbra
 * @date 2018/8/17
 * @description
 */
@Data
@ToString
public class WxTokenVo implements Serializable {

    private String wxToken;

    private String token;
}
