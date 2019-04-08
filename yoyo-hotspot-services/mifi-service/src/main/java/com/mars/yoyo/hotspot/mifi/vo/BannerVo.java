package com.mars.yoyo.hotspot.mifi.vo;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author tookbra
 * @date 2018/8/29
 * @description
 */
@Data
@ToString
public class BannerVo implements Serializable {

    /**
     * 视频地址
     */
    private String videoUrl;
    /**
     * 图片地址
     */
    private String imgUrl;
    /**
     *
     */
    private String name;
}
