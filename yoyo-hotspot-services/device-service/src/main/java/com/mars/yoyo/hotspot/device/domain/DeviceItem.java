package com.mars.yoyo.hotspot.device.domain;

import lombok.ToString;

import java.util.Date;
import java.util.Objects;
import javax.persistence.*;

@Table(name = "device_item")
@ToString
public class DeviceItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 充电宝id
     */
    @Column(name = "power_bank_id")
    private String powerBankId;

    /**
     * 槽位编号
     */
    private Short slot;

    /**
     * 0 20%电量 1 40%电量 2 60%电量 3 80%电量 4 100%电量 

     */
    private Short level;

    /**
     * 对应设备id
     */
    @Column(name = "device_id")
    private String deviceId;

    /**
     * 设备类型
     */
    @Column(name = "item_type")
    private Integer itemType;

    /**
     * 是否移除：0-未删除，1-已删除
     */
    private Integer deleted;

    /**
     * 是否被租借 1是 0否
     */
    private Boolean leased;

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
     * 获取充电宝id
     *
     * @return power_bank_id - 充电宝id
     */
    public String getPowerBankId() {
        return powerBankId;
    }

    /**
     * 设置充电宝id
     *
     * @param powerBankId 充电宝id
     */
    public void setPowerBankId(String powerBankId) {
        this.powerBankId = powerBankId;
    }

    /**
     * 获取槽位编号
     *
     * @return slot - 槽位编号
     */
    public Short getSlot() {
        return slot;
    }

    /**
     * 设置槽位编号
     *
     * @param slot 槽位编号
     */
    public void setSlot(Short slot) {
        this.slot = slot;
    }

    /**
     * 获取0 20%电量 1 40%电量 2 60%电量 3 80%电量 4 100%电量 

     *
     * @return level - 0 20%电量 1 40%电量 2 60%电量 3 80%电量 4 100%电量 

     */
    public Short getLevel() {
        return level;
    }

    /**
     * 设置0 20%电量 1 40%电量 2 60%电量 3 80%电量 4 100%电量 

     *
     * @param level 0 20%电量 1 40%电量 2 60%电量 3 80%电量 4 100%电量 

     */
    public void setLevel(Short level) {
        this.level = level;
    }

    /**
     * 获取对应设备id
     *
     * @return device_id - 对应设备id
     */
    public String getDeviceId() {
        return deviceId;
    }

    /**
     * 设置对应设备id
     *
     * @param deviceId 对应设备id
     */
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    /**
     * 获取设备类型
     *
     * @return item_type - 设备类型
     */
    public Integer getItemType() {
        return itemType;
    }

    /**
     * 设置设备类型
     *
     * @param itemType 设备类型
     */
    public void setItemType(Integer itemType) {
        this.itemType = itemType;
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
     * 获取是否被租借 1是 0否
     *
     * @return leased - 是否被租借 1是 0否
     */
    public Boolean getLeased() {
        return leased;
    }

    /**
     * 设置是否被租借 1是 0否
     *
     * @param leased 是否被租借 1是 0否
     */
    public void setLeased(Boolean leased) {
        this.leased = leased;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeviceItem that = (DeviceItem) o;
        return Objects.equals(powerBankId, that.powerBankId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(powerBankId);
    }
}