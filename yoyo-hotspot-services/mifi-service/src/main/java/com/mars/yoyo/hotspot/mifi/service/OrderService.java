package com.mars.yoyo.hotspot.mifi.service;

import com.github.binarywang.wxpay.exception.WxPayException;
import com.mars.yoyo.hotspot.mifi.domain.PayOrder;
import com.mars.yoyo.hotspot.mifi.dto.PrePayDto;
import com.mars.yoyo.hotspot.mifi.enums.ClientTypeEnum;
import com.mars.yoyo.hotspot.mifi.enums.PayChannelEnum;
import com.mars.yoyo.hotspot.mifi.vo.OrderConfirmVo;
import com.paypal.base.rest.PayPalRESTException;

/**
 * @author tookbra
 * @date 2018/5/21
 * @description
 */
public interface OrderService {

    /**
     * 添加订单
     * @param order
     * @return {@link PayOrder}
     */
    PayOrder addOrder(PayOrder order);

    /**
     * 更新订单
     * @param order
     * @return
     */
    PayOrder save(PayOrder order);

    /**
     * 查询订单
     * @param userId 用户id
     * @param orderNo 订单号
     * @return {@link PayOrder}
     */
    PayOrder findByOrderNo(int userId, String orderNo);

    /**
     * 支付路由
     * @param clientTypeEnum 客户端类型
     * @param path 返回路径
     * @param payChannelEnum 支付渠道
     * @param payOrder 订单对象
     * @return
     * @throws WxPayException
     * @throws PayPalRESTException
     */
    PrePayDto payAdpater(ClientTypeEnum clientTypeEnum, String path, PayChannelEnum payChannelEnum, PayOrder payOrder)
            throws WxPayException, PayPalRESTException;

    /**
     * 支付确认
     * @param paymentId
     * @param payerId
     * @throws PayPalRESTException
     */
    void orderConfirm(String paymentId, String payerId) throws PayPalRESTException;

    /**
     * 支付确认
     * @param paymentId
     * @return OrderConfirmVo
     */
    OrderConfirmVo orderConfirm(String paymentId) throws WxPayException;

    /**
     * paypal 支付通知
     * @param paymentId
     */
    void notifyPaypal(String paymentId);

    /**
     * wechar 支付通知
     * @param orderNo
     */
    void notifyWechat(String orderNo);

    /**
     * 查询成功的订单
     * @param userId 用户id
     * @param orderType 订单类型
     * @return
     */
    PayOrder findByOrderType(int userId, int orderType);

    /**
     * 查询成功的订单
     * @param userId 用户id
     * @param orderType 订单类型
     * @return
     */
    PayOrder findTop1ByOrderType(int userId, int orderType);

    /**
     * 查询支付是否成功
     * @param orderNo
     */
    boolean findByOrderNoQuery(String orderNo) throws WxPayException;
}
