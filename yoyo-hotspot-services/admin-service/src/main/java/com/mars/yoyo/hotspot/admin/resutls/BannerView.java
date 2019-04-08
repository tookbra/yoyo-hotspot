package com.mars.yoyo.hotspot.admin.resutls;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 广告
 *
 * @author admin
 * @Date 2018/9/4 13:20
 */
@Data
public class BannerView implements Serializable {

    private Integer id;

    private String name;

    private String imgUrl;

    private String videoUrl;

    private Integer state;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date mofidyTime;

}
