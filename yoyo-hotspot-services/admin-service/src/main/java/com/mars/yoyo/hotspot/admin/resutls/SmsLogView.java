package com.mars.yoyo.hotspot.admin.resutls;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 短信结果参数
 *
 * @author admin
 * @create 2018/5/17
 */
@Data
public class SmsLogView implements Serializable {

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
     * 短信类型 return 归还 other其它
     */
    private String smsType;

    /**
     * 发送批次
     */
    private String reqId;

    /**
     * 短信模板
     */
    private Integer templateId;

    /**
     * 创建时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

}
