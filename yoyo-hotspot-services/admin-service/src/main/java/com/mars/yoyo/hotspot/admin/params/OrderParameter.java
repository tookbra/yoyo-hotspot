package com.mars.yoyo.hotspot.admin.params;

import lombok.Data;

/**
 * 订单查询参数
 *
 * @author admin
 * @create 2018/5/31
 */
@Data
public class OrderParameter extends PageParameter{

    /**
     * 订单编号
     */
    private String searchText;

    /**
     * 支付渠道 1 账户余额 2微信支付 3支付宝 4paypal
     */
    private Integer payChannel;

    /**
     * 订单类型
     */
    private Integer orderType;

    /**
     * 状态，0订单生成 1支付中 2支付成功 3支付失败
     */
    private Integer state;

    /**
     * 起始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;

}
