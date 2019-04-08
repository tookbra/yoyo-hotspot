package com.mars.yoyo.hotspot.admin.resutls;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单
 *
 * @author admin
 * @Date 2018/8/23 10:25
 */
@Data
public class PayOrderView implements Serializable {

    /**
     * 订单编号
     */
    private String orderId;

    /**
     * 状态，0订单生成 1支付中 2支付成功 3支付失败
     */
    private Integer state;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 是否国际版本 0否 1是
     */
    private Integer en;

    /**
     * 套餐名称
     */
    private String productName;

    /**
     * 创建时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 支付成功时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date paySuccessTime;

    /**
     * 设备编号
     */
    private String powerBankId;

    /**
     * 密码
     */
    private String passowrd;

    /**
     * 是否归还 0否 1是
     */
    private Integer returned;

    /**
     * 订单类型 1押金 2充值 3租借 4续费 5 归还
     */
    private Integer orderType;

    /**
     * 币种 默认人民币
     */
    private String currency;

    /**
     * 支付金额
     */
    private BigDecimal amount;

    /**
     * 支付渠道 1 账户余额 2微信支付 3支付宝 4paypal
     */
    private Integer payChannel;

}
