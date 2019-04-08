package com.mars.yoyo.hotspot.admin.params;

import lombok.Data;

import java.io.Serializable;

/**
 * 广告参数
 *
 * @author admin
 * @Date 2018/9/4 13:24
 */
@Data
public class BannerParameter implements Serializable {

    private Integer id;

    private String name;

    private String imgUrl;

    private String videoUrl;

    private Integer state;

}
