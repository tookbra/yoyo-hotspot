package com.mars.yoyo.hotspot.ms.domain;

import java.util.Date;
import javax.persistence.*;

@Table(name = "sms_template")
public class SmsTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String code;

    /**
     * 模板内容
     */
    private String content;

    /**
     * 是否启用 1是 0 否
     */
    private Boolean state;

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
     * @return code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 获取模板内容
     *
     * @return content - 模板内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置模板内容
     *
     * @param content 模板内容
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 获取是否启用 1是 0 否
     *
     * @return state - 是否启用 1是 0 否
     */
    public Boolean getState() {
        return state;
    }

    /**
     * 设置是否启用 1是 0 否
     *
     * @param state 是否启用 1是 0 否
     */
    public void setState(Boolean state) {
        this.state = state;
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