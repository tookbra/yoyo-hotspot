package com.mars.yoyo.hotspot.mifi.domain;

import java.util.Date;
import javax.persistence.*;

@Table(name = "user_auth")
public class UserAuth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private Integer userId;

    /**
     * 身份类型 phone,wechat,qq,weibo
     */
    @Column(name = "auth_type")
    private String authType;

    /**
     * 身份唯一标识
     */
    @Column(name = "open_id")
    private String openId;

    /**
     * 密码或token
     */
    private String secret;

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
     * 获取用户id
     *
     * @return user_id - 用户id
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * 设置用户id
     *
     * @param userId 用户id
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * 获取身份类型 phone,wechat,qq,weibo
     *
     * @return auth_type - 身份类型 phone,wechat,qq,weibo
     */
    public String getAuthType() {
        return authType;
    }

    /**
     * 设置身份类型 phone,wechat,qq,weibo
     *
     * @param authType 身份类型 phone,wechat,qq,weibo
     */
    public void setAuthType(String authType) {
        this.authType = authType;
    }

    /**
     * 获取身份唯一标识
     *
     * @return open_id - 身份唯一标识
     */
    public String getOpenId() {
        return openId;
    }

    /**
     * 设置身份唯一标识
     *
     * @param openId 身份唯一标识
     */
    public void setOpenId(String openId) {
        this.openId = openId;
    }

    /**
     * 获取密码或token
     *
     * @return secret - 密码或token
     */
    public String getSecret() {
        return secret;
    }

    /**
     * 设置密码或token
     *
     * @param secret 密码或token
     */
    public void setSecret(String secret) {
        this.secret = secret;
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