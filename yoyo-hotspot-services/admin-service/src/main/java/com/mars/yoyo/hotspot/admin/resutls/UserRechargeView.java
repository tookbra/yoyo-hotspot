package com.mars.yoyo.hotspot.admin.resutls;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 充值记录返回参数
 *
 * @author admin
 * @create 2018/6/6
 */
@Data
public class UserRechargeView implements Serializable {

    /**
     * 编号
     */
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 国家(通过区号进行判断)
     */
    private String country;

    /**
     * 是否国际版本 0否 1是
     */
    private Boolean en;

    /**
     * 充值的金额
     */
    private BigDecimal amount;

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 状态 0 初始 1处理中 2成功 3失败
     */
    private Integer status;

    /**
     * 支付渠道 1 账户余额 2微信支付 3支付宝 4paypal
     */
    private Integer payChannel;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

}
