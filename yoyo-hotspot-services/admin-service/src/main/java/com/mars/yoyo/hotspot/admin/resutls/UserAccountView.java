package com.mars.yoyo.hotspot.admin.resutls;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户帐号信息
 *
 * @author admin
 * @create 2018/5/16
 */
@Data
public class UserAccountView implements Serializable {

    private Integer userId;

    /**
     * 姓名
     */
    private String name;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 账户余额
     */
    private BigDecimal balance;

    /**
     * 账户余额，美元
     */
    private BigDecimal balanceEn;

    /**
     * 押金
     */
    private BigDecimal deposit;

    /**
     * 押金
     */
    private BigDecimal depositEn;

    /**
     * 优惠券数量
     */
    private Integer couponNum;

    /**
     * 红包数量
     */
    private Integer redEnvelopeNum;

    /**
     * 积分
     */
    private Integer integral;

    /**
     * 创建时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 修改时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date modifyTime;

}
