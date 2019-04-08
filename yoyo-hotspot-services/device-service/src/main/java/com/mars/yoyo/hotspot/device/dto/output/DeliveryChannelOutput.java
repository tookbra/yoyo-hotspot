package com.mars.yoyo.hotspot.device.dto.output;

import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/**
 * @author tookbra
 * @date 2018/6/15
 * @description
 */
@Slf4j
@Data
@ToString
public class DeliveryChannelOutput implements Serializable {

    /**
     * 视频地址
     */
    private String videoUrl;
    /**
     * 图片地址
     */
    private String imgUrl;
}
