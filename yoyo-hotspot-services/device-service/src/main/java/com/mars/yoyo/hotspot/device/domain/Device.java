package com.mars.yoyo.hotspot.device.domain;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 机柜id
     */
    @Column(name = "box_id")
    private String boxId;

    /**
     * md5(box_id)
     */
    private String token;

    /**
     * 机柜版本号
     */
    private String version;

    /**
     * 剩余充电宝个数
     */
    @Column(name = "remain_num")
    private Short remainNum;

    /**
     * 上次心跳时间
     */
    @Column(name = "last_heart")
    private Date lastHeart;

    /**
     * 服务器地址
     */
    @Column(name = "server_address")
    private String serverAddress;

    /**
     * 服务器端口
     */
    @Column(name = "server_port")
    private Short serverPort;

    /**
     * 精度
     */
    private BigDecimal longitude;

    /**
     * 纬度
     */
    private BigDecimal latitude;

    /**
     * 存放地址
     */
    private String address;

    /**
     * 是否移除：0-未删除，1-已删除
     */
    private Integer deleted;

    /**
     * 投放渠道
     */
    @Column(name = "delivery_channel")
    private Integer deliveryChannel;

    @Column(name = "delivery_name")
    private String deliveryName;

    @Column(name = "address_en")
    private String addressEn;

    @Column(name = "address_detail")
    private String addressDetail;

    @Column(name = "address_detail_en")
    private String addressDetailEn;

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
     * 获取机柜id
     *
     * @return box_id - 机柜id
     */
    public String getBoxId() {
        return boxId;
    }

    /**
     * 设置机柜id
     *
     * @param boxId 机柜id
     */
    public void setBoxId(String boxId) {
        this.boxId = boxId;
    }

    /**
     * 获取md5(box_id)
     *
     * @return token - md5(box_id)
     */
    public String getToken() {
        return token;
    }

    /**
     * 设置md5(box_id)
     *
     * @param token md5(box_id)
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * 获取机柜版本号
     *
     * @return version - 机柜版本号
     */
    public String getVersion() {
        return version;
    }

    /**
     * 设置机柜版本号
     *
     * @param version 机柜版本号
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * 获取剩余充电宝个数
     *
     * @return remain_num - 剩余充电宝个数
     */
    public Short getRemainNum() {
        return remainNum;
    }

    /**
     * 设置剩余充电宝个数
     *
     * @param remainNum 剩余充电宝个数
     */
    public void setRemainNum(Short remainNum) {
        this.remainNum = remainNum;
    }

    /**
     * 获取上次心跳时间
     *
     * @return last_heart - 上次心跳时间
     */
    public Date getLastHeart() {
        return lastHeart;
    }

    /**
     * 设置上次心跳时间
     *
     * @param lastHeart 上次心跳时间
     */
    public void setLastHeart(Date lastHeart) {
        this.lastHeart = lastHeart;
    }

    /**
     * 获取服务器地址
     *
     * @return server_address - 服务器地址
     */
    public String getServerAddress() {
        return serverAddress;
    }

    /**
     * 设置服务器地址
     *
     * @param serverAddress 服务器地址
     */
    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    /**
     * 获取服务器端口
     *
     * @return server_port - 服务器端口
     */
    public Short getServerPort() {
        return serverPort;
    }

    /**
     * 设置服务器端口
     *
     * @param serverPort 服务器端口
     */
    public void setServerPort(Short serverPort) {
        this.serverPort = serverPort;
    }

    /**
     * 获取精度
     *
     * @return longitude - 精度
     */
    public BigDecimal getLongitude() {
        return longitude;
    }

    /**
     * 设置精度
     *
     * @param longitude 精度
     */
    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    /**
     * 获取纬度
     *
     * @return latitude - 纬度
     */
    public BigDecimal getLatitude() {
        return latitude;
    }

    /**
     * 设置纬度
     *
     * @param latitude 纬度
     */
    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    /**
     * 获取存放地址
     *
     * @return address - 存放地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置存放地址
     *
     * @param address 存放地址
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 获取是否移除：0-未删除，1-已删除
     *
     * @return deleted - 是否移除：0-未删除，1-已删除
     */
    public Integer getDeleted() {
        return deleted;
    }

    /**
     * 设置是否移除：0-未删除，1-已删除
     *
     * @param deleted 是否移除：0-未删除，1-已删除
     */
    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    /**
     * 获取投放渠道
     *
     * @return delivery_channel - 投放渠道
     */
    public Integer getDeliveryChannel() {
        return deliveryChannel;
    }

    /**
     * 设置投放渠道
     *
     * @param deliveryChannel 投放渠道
     */
    public void setDeliveryChannel(Integer deliveryChannel) {
        this.deliveryChannel = deliveryChannel;
    }

    /**
     * @return delivery_name
     */
    public String getDeliveryName() {
        return deliveryName;
    }

    /**
     * @param deliveryName
     */
    public void setDeliveryName(String deliveryName) {
        this.deliveryName = deliveryName;
    }

    /**
     * @return address_en
     */
    public String getAddressEn() {
        return addressEn;
    }

    /**
     * @param addressEn
     */
    public void setAddressEn(String addressEn) {
        this.addressEn = addressEn;
    }

    /**
     * @return address_detail
     */
    public String getAddressDetail() {
        return addressDetail;
    }

    /**
     * @param addressDetail
     */
    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }

    /**
     * @return address_detail_en
     */
    public String getAddressDetailEn() {
        return addressDetailEn;
    }

    /**
     * @param addressDetailEn
     */
    public void setAddressDetailEn(String addressDetailEn) {
        this.addressDetailEn = addressDetailEn;
    }
}