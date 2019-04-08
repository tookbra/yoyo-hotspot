package com.mars.yoyo.hotspot.ms.domain;

import java.util.Date;
import javax.persistence.*;

@Table(name = "sms_mo")
public class SmsMo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 扩展号
     */
    @Column(name = "sub_id")
    private String subId;

    private String content;

    /**
     * 接受时间
     */
    @Column(name = "receive_time")
    private Date receiveTime;

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
     * 获取扩展号
     *
     * @return sub_id - 扩展号
     */
    public String getSubId() {
        return subId;
    }

    /**
     * 设置扩展号
     *
     * @param subId 扩展号
     */
    public void setSubId(String subId) {
        this.subId = subId;
    }

    /**
     * @return content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 获取接受时间
     *
     * @return receive_time - 接受时间
     */
    public Date getReceiveTime() {
        return receiveTime;
    }

    /**
     * 设置接受时间
     *
     * @param receiveTime 接受时间
     */
    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }
}