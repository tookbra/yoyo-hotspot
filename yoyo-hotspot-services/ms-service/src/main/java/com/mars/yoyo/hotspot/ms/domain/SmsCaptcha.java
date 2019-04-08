package com.mars.yoyo.hotspot.ms.domain;

import java.util.Date;
import javax.persistence.*;

@Table(name = "sms_captcha")
public class SmsCaptcha {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 手机号
     */
    private String phone;

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
    @Column(name = "sms_type")
    private String smsType;

    @Column(name = "req_id")
    private String reqId;

    /**
     * 短信模板id
     */
    @Column(name = "template_id")
    private Integer templateId;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "modify_time")
    private Date modifyTime;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取手机号
     *
     * @return phone - 手机号
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置手机号
     *
     * @param phone 手机号
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 获取短信内容
     *
     * @return content - 短信内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置短信内容
     *
     * @param content 短信内容
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 获取验证码
     *
     * @return captcha - 验证码
     */
    public String getCaptcha() {
        return captcha;
    }

    /**
     * 设置验证码
     *
     * @param captcha 验证码
     */
    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    /**
     * 获取短信类型 reg注册
     *
     * @return sms_type - 短信类型 reg注册
     */
    public String getSmsType() {
        return smsType;
    }

    /**
     * 设置短信类型 reg注册
     *
     * @param smsType 短信类型 reg注册
     */
    public void setSmsType(String smsType) {
        this.smsType = smsType;
    }

    /**
     * @return req_id
     */
    public String getReqId() {
        return reqId;
    }

    /**
     * @param reqId
     */
    public void setReqId(String reqId) {
        this.reqId = reqId;
    }

    /**
     * 获取短信模板id
     *
     * @return template_id - 短信模板id
     */
    public Integer getTemplateId() {
        return templateId;
    }

    /**
     * 设置短信模板id
     *
     * @param templateId 短信模板id
     */
    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return modify_time
     */
    public Date getModifyTime() {
        return modifyTime;
    }

    /**
     * @param modifyTime
     */
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}