package com.mars.yoyo.hotspot.admin.resutls;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 租赁记录
 * @author admin
 * @create 2018/5/17
 */
@Data
public class LeaseView implements Serializable {

    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 用户名称
     */
    private String name;

    /**
     * 号码
     */
    private String phone;

    /**
     * 机柜id
     */
    private String deviceId;

    /**
     * 设备id
     */
    private String powerBankId;

    /**
     * 产品id
     */
    private Integer productId;

    /**
     * 产品类型
     */
    private Integer productType;

    /**
     * 产品名称
     */
    private String productName;

    /**
     * 密码
     */
    private String passowrd;

    /**
     * 租赁单号
     */
    private String leaseNo;

    /**
     * 模式转换 上一个租借编号
     */
    private String lastLeaseNo;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 租赁时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date rentTime;

    /**
     * 是否归还 0否 1是
     */
    private Integer returned;

    /**
     * 归还时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date returnTime;

    /**
     * 预计过期时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date expectedReturnTime;

    /**
     * 是否国际版
     */
    private Integer langEn;

    /**
     * 是否结束租赁 0 否 1 是
     */
    private Integer overed;

    /**
     * 租赁地址
     */
    private String address;

    /**
     * 设备状态 0 初始 1成功 2失败
     */
    private Integer state;

    /**
     * 到期短信是否发送 0否 1是
     */
    private Integer expiredSms;

    /**
     * 即将到期短信是否发送 0否 1是
     */
    private Integer smsExpiring;

    /**
     * 封顶短信提醒 0否 1是
     */
    private Integer smsCap;

    /**
     * 槽位号
     */
    private Integer slot;

    /**
     * 创建时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

}
