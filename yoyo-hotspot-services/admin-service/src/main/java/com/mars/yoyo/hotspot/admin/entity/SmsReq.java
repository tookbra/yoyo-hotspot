package com.mars.yoyo.hotspot.admin.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "sms_req")
public class SmsReq {
    /**
     * 发送批次
     */
    @Id
    @Column(name = "req_id")
    private String reqId;

    /**
     * 回执时间
     */
    @Column(name = "receive_time")
    private Date receiveTime;

    /**
     * 状态报告码
     */
    private String state;

    /**
     * 下发时间
     */
    @Column(name = "send_time")
    private Date sendTime;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "modify_time")
    private Date modifyTime;

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
     * 获取回执时间
     *
     * @return receive_time - 回执时间
     */
    public Date getReceiveTime() {
        return receiveTime;
    }

    /**
     * 设置回执时间
     *
     * @param receiveTime 回执时间
     */
    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }

    /**
     * 获取状态报告码
     *
     * @return state - 状态报告码
     */
    public String getState() {
        return state;
    }

    /**
     * 设置状态报告码
     *
     * @param state 状态报告码
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * 获取下发时间
     *
     * @return send_time - 下发时间
     */
    public Date getSendTime() {
        return sendTime;
    }

    /**
     * 设置下发时间
     *
     * @param sendTime 下发时间
     */
    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
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