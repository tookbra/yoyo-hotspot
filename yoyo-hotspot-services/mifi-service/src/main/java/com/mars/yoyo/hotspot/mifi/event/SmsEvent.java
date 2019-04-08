package com.mars.yoyo.hotspot.mifi.event;

import lombok.Data;
import lombok.ToString;

import java.util.Map;

/**
 * @author tookbra
 * @date 2018/7/16
 * @description
 */
@Data
@ToString
public class SmsEvent {

    private int userId;

    private String phone;

    private String type;

    private Map<String, Object> paramMap;
}
