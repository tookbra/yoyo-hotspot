package com.mars.yoyo.hotspot.admin.resutls;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 短信模版
 *
 * @author admin
 * @create 2018/5/23
 */
@Data
public class SmsTemplateView implements Serializable {

    private Integer id;

    private String name;

    private String code;

    /**
     * 模板内容
     */
    private String content;

    /**
     * 是否启用 1是 0 否
     */
    private Integer state;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private Date modifyTime;

}
