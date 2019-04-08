package com.mars.yoyo.hotspot.admin.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "sms_log")
public class SmsLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 用户表
     */
    @Column(name = "user_id")
    private Integer userId;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 短信内容
     */
    private String content;

    /**
     * 短信类型 return 归还 other其它
     */
    @Column(name = "sms_type")
    private String smsType;

    /**
     * 发送批次
     */
    @Column(name = "req_id")
    private String reqId;

    /**
     * 短信模板
     */
    @Column(name = "template_id")
    private Integer templateId;

    /**
     * 创建时间
     */
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
     * 获取用户表
     *
     * @return user_id - 用户表
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * 设置用户表
     *
     * @param userId 用户表
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
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
     * 获取短信类型 return 归还 other其它
     *
     * @return sms_type - 短信类型 return 归还 other其它
     */
    public String getSmsType() {
        return smsType;
    }

    /**
     * 设置短信类型 return 归还 other其它
     *
     * @param smsType 短信类型 return 归还 other其它
     */
    public void setSmsType(String smsType) {
        this.smsType = smsType;
    }

    /**
     * 获取发送批次
     *
     * @return req_id - 发送批次
     */
    public String getReqId() {
        return reqId;
    }

    /**
     * 设置发送批次
     *
     * @param reqId 发送批次
     */
    public void setReqId(String reqId) {
        this.reqId = reqId;
    }

    /**
     * 获取短信模板
     *
     * @return template_id - 短信模板
     */
    public Integer getTemplateId() {
        return templateId;
    }

    /**
     * 设置短信模板
     *
     * @param templateId 短信模板
     */
    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
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