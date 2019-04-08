package com.mars.yoyo.hotspot.mifi.domain;

import java.util.Date;
import javax.persistence.*;

@Table(name = "flow_order")
public class FlowOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 流水号
     */
    @Column(name = "trans_id")
    private String transId;

    /**
     * 租借id
     */
    @Column(name = "lease_id")
    private Integer leaseId;

    private String imei;

    /**
     * 套餐id
     */
    @Column(name = "pkg_type_id")
    private String pkgTypeId;

    /**
     * 套餐记录id
     */
    @Column(name = "record_id")
    private String recordId;

    private Boolean stoped;

    /**
     * 创建时间
     */
    @Column(name = "created_time")
    private Date createdTime;

    /**
     * 修改时间
     */
    @Column(name = "modify_time")
    private Date modifyTime;

    /**
     * 密码
     */
    @Transient
    private String pwd;

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
     * 获取流水号
     *
     * @return trans_id - 流水号
     */
    public String getTransId() {
        return transId;
    }

    /**
     * 设置流水号
     *
     * @param transId 流水号
     */
    public void setTransId(String transId) {
        this.transId = transId;
    }

    /**
     * 获取租借id
     *
     * @return lease_id - 租借id
     */
    public Integer getLeaseId() {
        return leaseId;
    }

    /**
     * 设置租借id
     *
     * @param leaseId 租借id
     */
    public void setLeaseId(Integer leaseId) {
        this.leaseId = leaseId;
    }

    /**
     * @return imei
     */
    public String getImei() {
        return imei;
    }

    /**
     * @param imei
     */
    public void setImei(String imei) {
        this.imei = imei;
    }

    /**
     * 获取套餐id
     *
     * @return pkg_type_id - 套餐id
     */
    public String getPkgTypeId() {
        return pkgTypeId;
    }

    /**
     * 设置套餐id
     *
     * @param pkgTypeId 套餐id
     */
    public void setPkgTypeId(String pkgTypeId) {
        this.pkgTypeId = pkgTypeId;
    }

    /**
     * 获取套餐记录id
     *
     * @return record_id - 套餐记录id
     */
    public String getRecordId() {
        return recordId;
    }

    /**
     * 设置套餐记录id
     *
     * @param recordId 套餐记录id
     */
    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    /**
     * @return stoped
     */
    public Boolean getStoped() {
        return stoped;
    }

    /**
     * @param stoped
     */
    public void setStoped(Boolean stoped) {
        this.stoped = stoped;
    }

    /**
     * 获取创建时间
     *
     * @return created_time - 创建时间
     */
    public Date getCreatedTime() {
        return createdTime;
    }

    /**
     * 设置创建时间
     *
     * @param createdTime 创建时间
     */
    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    /**
     * 获取修改时间
     *
     * @return modify_time - 修改时间
     */
    public Date getModifyTime() {
        return modifyTime;
    }

    /**
     * 设置修改时间
     *
     * @param modifyTime 修改时间
     */
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}