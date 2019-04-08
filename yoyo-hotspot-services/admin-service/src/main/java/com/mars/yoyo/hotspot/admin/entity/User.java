package com.mars.yoyo.hotspot.admin.entity;

import java.util.Date;
import javax.persistence.*;

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 0男 1 女
     */
    private Integer sex;

    /**
     * 身份证号
     */
    @Column(name = "id_card")
    private String idCard;

    /**
     * 是否交纳押金 0 否 1 是
     */
    private Integer deposited;

    /**
     * 是否认证 0否 1是
     */
    private Integer certified;

    /**
     * 最后登录ip
     */
    @Column(name = "last_login_ip")
    private String lastLoginIp;

    /**
     * 是否禁用 0 否 1 是
     */
    private Integer state;

    /**
     * 生日
     */
    private String birthday;

    /**
     * 手机号码区号
     */
    @Column(name = "country_area_code")
    private String countryAreaCode;

    /**
     * 注册时间

     */
    @Column(name = "register_time")
    private Date registerTime;

    /**
     * 注册ip
     */
    @Column(name = "register_ip")
    private String registerIp;

    /**
     * 是否国际版本 0否 1是
     */
    private Integer en;

    private String nickname;

    @Column(name = "headImgUrl")
    private String headimgurl;

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
     * 获取姓名
     *
     * @return name - 姓名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置姓名
     *
     * @param name 姓名
     */
    public void setName(String name) {
        this.name = name;
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
     * 获取0男 1 女
     *
     * @return sex - 0男 1 女
     */
    public Integer getSex() {
        return sex;
    }

    /**
     * 设置0男 1 女
     *
     * @param sex 0男 1 女
     */
    public void setSex(Integer sex) {
        this.sex = sex;
    }

    /**
     * 获取身份证号
     *
     * @return id_card - 身份证号
     */
    public String getIdCard() {
        return idCard;
    }

    /**
     * 设置身份证号
     *
     * @param idCard 身份证号
     */
    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    /**
     * 获取是否交纳押金 0 否 1 是
     *
     * @return deposited - 是否交纳押金 0 否 1 是
     */
    public Integer getDeposited() {
        return deposited;
    }

    /**
     * 设置是否交纳押金 0 否 1 是
     *
     * @param deposited 是否交纳押金 0 否 1 是
     */
    public void setDeposited(Integer deposited) {
        this.deposited = deposited;
    }

    /**
     * 获取是否认证 0否 1是
     *
     * @return certified - 是否认证 0否 1是
     */
    public Integer getCertified() {
        return certified;
    }

    /**
     * 设置是否认证 0否 1是
     *
     * @param certified 是否认证 0否 1是
     */
    public void setCertified(Integer certified) {
        this.certified = certified;
    }

    /**
     * 获取最后登录ip
     *
     * @return last_login_ip - 最后登录ip
     */
    public String getLastLoginIp() {
        return lastLoginIp;
    }

    /**
     * 设置最后登录ip
     *
     * @param lastLoginIp 最后登录ip
     */
    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    /**
     * 获取是否禁用 0 否 1 是
     *
     * @return state - 是否禁用 0 否 1 是
     */
    public Integer getState() {
        return state;
    }

    /**
     * 设置是否禁用 0 否 1 是
     *
     * @param state 是否禁用 0 否 1 是
     */
    public void setState(Integer state) {
        this.state = state;
    }

    /**
     * 获取生日
     *
     * @return birthday - 生日
     */
    public String getBirthday() {
        return birthday;
    }

    /**
     * 设置生日
     *
     * @param birthday 生日
     */
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    /**
     * 获取手机号码区号
     *
     * @return country_area_code - 手机号码区号
     */
    public String getCountryAreaCode() {
        return countryAreaCode;
    }

    /**
     * 设置手机号码区号
     *
     * @param countryAreaCode 手机号码区号
     */
    public void setCountryAreaCode(String countryAreaCode) {
        this.countryAreaCode = countryAreaCode;
    }

    /**
     * 获取注册时间

     *
     * @return register_time - 注册时间

     */
    public Date getRegisterTime() {
        return registerTime;
    }

    /**
     * 设置注册时间

     *
     * @param registerTime 注册时间

     */
    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    /**
     * 获取注册ip
     *
     * @return register_ip - 注册ip
     */
    public String getRegisterIp() {
        return registerIp;
    }

    /**
     * 设置注册ip
     *
     * @param registerIp 注册ip
     */
    public void setRegisterIp(String registerIp) {
        this.registerIp = registerIp;
    }

    /**
     * 获取是否国际版本 0否 1是
     *
     * @return en - 是否国际版本 0否 1是
     */
    public Integer getEn() {
        return en;
    }

    /**
     * 设置是否国际版本 0否 1是
     *
     * @param en 是否国际版本 0否 1是
     */
    public void setEn(Integer en) {
        this.en = en;
    }

    /**
     * @return nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * @param nickname
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * @return headImgUrl
     */
    public String getHeadimgurl() {
        return headimgurl;
    }

    /**
     * @param headimgurl
     */
    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
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