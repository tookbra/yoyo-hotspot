package com.mars.yoyo.hotspot.mifi.domain;

import java.util.Date;
import javax.persistence.*;

@Table(name = "user_coupon")
public class UserCoupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 用户id

     */
    @Column(name = "user_id")
    private Integer userId;

    /**
     * 优惠券id
     */
    @Column(name = "coupon_id")
    private Integer couponId;

    /**
     * 是否使用 0 否 1 是
     */
    private Boolean state;

    /**
     * 使用时间
     */
    @Column(name = "use_time")
    private Date useTime;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 修改时间
     */
    @Column(name = "mofidy_time")
    private Date mofidyTime;

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
     * 获取优惠券id
     *
     * @return coupon_id - 优惠券id
     */
    public Integer getCouponId() {
        return couponId;
    }

    /**
     * 设置优惠券id
     *
     * @param couponId 优惠券id
     */
    public void setCouponId(Integer couponId) {
        this.couponId = couponId;
    }

    /**
     * 获取是否使用 0 否 1 是
     *
     * @return state - 是否使用 0 否 1 是
     */
    public Boolean getState() {
        return state;
    }

    /**
     * 设置是否使用 0 否 1 是
     *
     * @param state 是否使用 0 否 1 是
     */
    public void setState(Boolean state) {
        this.state = state;
    }

    /**
     * 获取使用时间
     *
     * @return use_time - 使用时间
     */
    public Date getUseTime() {
        return useTime;
    }

    /**
     * 设置使用时间
     *
     * @param useTime 使用时间
     */
    public void setUseTime(Date useTime) {
        this.useTime = useTime;
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
     * 获取修改时间
     *
     * @return mofidy_time - 修改时间
     */
    public Date getMofidyTime() {
        return mofidyTime;
    }

    /**
     * 设置修改时间
     *
     * @param mofidyTime 修改时间
     */
    public void setMofidyTime(Date mofidyTime) {
        this.mofidyTime = mofidyTime;
    }
}