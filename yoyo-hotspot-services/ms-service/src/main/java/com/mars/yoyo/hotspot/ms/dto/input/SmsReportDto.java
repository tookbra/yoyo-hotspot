package com.mars.yoyo.hotspot.ms.dto.input;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author tookbra
 * @date 2018/5/16
 * @description
 */
@Data
@ToString
public class SmsReportDto {

    /**
     * 消息类型
     * 0 上行消息 2 状态报告
     */
    private int msgtype;
    /**
     * 手机号码
     */
    private String phone;
    /**
     * 扩展号
     */
    private String subid;
    /**
     * ReqId 发送批次 ID
     */
    private String reqid;
    /**
     * 上行短信内容
     */
    private String content;
    /**
     * 消息接收时间
     */
    private String receivetime;
    /**
     * 短信下发时间
     */
    private String sendtime;
    /**
     * 状态报告码
     */
    private String state;
}
