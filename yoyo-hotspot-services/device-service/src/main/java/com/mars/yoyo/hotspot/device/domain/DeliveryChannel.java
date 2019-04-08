package com.mars.yoyo.hotspot.device.domain;

import java.util.Date;
import javax.persistence.*;

@Table(name = "delivery_channel")
public class DeliveryChannel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 渠道名称
     */
    private String name;

    /**
     * 渠道码
     */
    private String token;

    /**
     * 店家名称
     */
    @Column(name = "store_name")
    private String storeName;

    /**
     * 店家视频地址
     */
    @Column(name = "store_video")
    private String storeVideo;

    /**
     * 店家视频封面
     */
    @Column(name = "store_img")
    private String storeImg;

    /**
     * 是否有押金
     */
    private Boolean deposit;

    /**
     * 是否有活动 1是0否
     */
    private Boolean activityed;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
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
     * 获取渠道名称
     *
     * @return name - 渠道名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置渠道名称
     *
     * @param name 渠道名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取渠道码
     *
     * @return token - 渠道码
     */
    public String getToken() {
        return token;
    }

    /**
     * 设置渠道码
     *
     * @param token 渠道码
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * 获取店家名称
     *
     * @return store_name - 店家名称
     */
    public String getStoreName() {
        return storeName;
    }

    /**
     * 设置店家名称
     *
     * @param storeName 店家名称
     */
    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    /**
     * 获取店家视频地址
     *
     * @return store_video - 店家视频地址
     */
    public String getStoreVideo() {
        return storeVideo;
    }

    /**
     * 设置店家视频地址
     *
     * @param storeVideo 店家视频地址
     */
    public void setStoreVideo(String storeVideo) {
        this.storeVideo = storeVideo;
    }

    /**
     * 获取店家视频封面
     *
     * @return store_img - 店家视频封面
     */
    public String getStoreImg() {
        return storeImg;
    }

    /**
     * 设置店家视频封面
     *
     * @param storeImg 店家视频封面
     */
    public void setStoreImg(String storeImg) {
        this.storeImg = storeImg;
    }

    /**
     * 获取是否有押金
     *
     * @return deposit - 是否有押金
     */
    public Boolean getDeposit() {
        return deposit;
    }

    /**
     * 设置是否有押金
     *
     * @param deposit 是否有押金
     */
    public void setDeposit(Boolean deposit) {
        this.deposit = deposit;
    }

    /**
     * 获取是否有活动 1是0否
     *
     * @return activityed - 是否有活动 1是0否
     */
    public Boolean getActivityed() {
        return activityed;
    }

    /**
     * 设置是否有活动 1是0否
     *
     * @param activityed 是否有活动 1是0否
     */
    public void setActivityed(Boolean activityed) {
        this.activityed = activityed;
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
     * 获取更新时间
     *
     * @return modify_time - 更新时间
     */
    public Date getModifyTime() {
        return modifyTime;
    }

    /**
     * 设置更新时间
     *
     * @param modifyTime 更新时间
     */
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}