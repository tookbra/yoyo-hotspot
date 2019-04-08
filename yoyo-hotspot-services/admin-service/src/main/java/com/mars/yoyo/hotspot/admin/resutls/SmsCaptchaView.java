package com.mars.yoyo.hotspot.admin.resutls;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 短信验证码
 *
 * @author admin
 * @create 2018/6/6
 */
@Data
public class SmsCaptchaView implements Serializable {

    private Integer id;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 国家(通过区号进行判断)
     */
    private String country;

    /**
     * 是否国际版本 0否 1是
     */
    private Integer en;

    /**
     * 短信内容
     */
    private String content;

    /**
     * 验证码
     */
    private String captcha;

    /**
     * 短信类型 reg注册
     */
    private String smsType;

    /**
     * 发送批次
     */
    private String reqId;

    /**
     * 短信模板id
     */
    private Integer templateId;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

}
