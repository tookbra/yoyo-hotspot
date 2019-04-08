package com.mars.yoyo.hotspot.mifi.service;

import com.github.binarywang.wxpay.exception.WxPayException;
import com.mars.yoyo.hotspot.common.dps.secutiry.UserEnv;
import com.mars.yoyo.hotspot.mifi.domain.Lease;
import com.mars.yoyo.hotspot.mifi.dto.PaymentDto;
import com.mars.yoyo.hotspot.mifi.dto.PrePayDto;
import com.mars.yoyo.hotspot.mifi.dto.input.RentInputDto;
import com.mars.yoyo.hotspot.mifi.dto.input.RentReportInput;
import com.mars.yoyo.hotspot.mifi.vo.LeaseDetailVo;
import com.mars.yoyo.hotspot.mifi.vo.LeaseRecordVo;
import com.mars.yoyo.hotspot.mifi.vo.LeaseStateVo;
import com.mars.yoyo.hotspot.mifi.vo.PreLeaseVo;
import com.paypal.base.rest.PayPalRESTException;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author tookbra
 * @date 2018/7/12
 * @description
 */
public interface LeaseService {

    /**
     * 查询当前租赁状态
     * @param userId 用户id
     * @return {@link LeaseStateVo}
     */
    LeaseStateVo findLeaseState(int userId);

    /**
     * 查询当前使用详情
     * @param userId 用户id
     * @param lang 用户语言
     * @param convert 是否需要换算
     * @return {@link LeaseDetailVo}
     */
    LeaseDetailVo findDetail(int userId, String lang, boolean convert);

    /**
     * 查询我的租赁记录
     * @param userId 用户ID
     * @param lang 用户语言
     * @return {@link LeaseRecordVo}
     */
    LeaseRecordVo findMyLeaseList(int userId, String lang);

    /**
     * 小时借
     * @param userEnv 用户环境
     * @param productId 产品id
     * @return {@link LeaseDetailVo}
     */
    LeaseDetailVo rentHour(UserEnv userEnv, Integer productId);

    /**
     * 查询正在租用的记录
     * @return
     */
    List<Lease> findLease();

    /**
     * 查询租借记录
     * @param leaseId 租借id
     * @return
     */
    Lease findLeaseById(int leaseId);

    /**
     * 查询租借记录
     * @param orderId 订单id
     * @return {@link Lease}
     */
    Lease findByOrderId(String orderId);

    /**
     * 添加租赁记录
     * @param lease
     */
    void addLease(Lease lease);

    /**
     * 更换套餐
     * @param userEnv 用户环境
     * @param productId 产品id
     * @param convert 是否转换
     * @return {@link PreLeaseVo}
     */
    PreLeaseVo changeRent(UserEnv userEnv, Integer productId, boolean convert);

    /**
     * 统一下单
     * @param userEnv 用户环境
     * @param rentInputDto
     * @return
     * @throws WxPayException
     * @throws PayPalRESTException
     */
    PrePayDto unifiedOrder(UserEnv userEnv, RentInputDto rentInputDto) throws WxPayException, PayPalRESTException;

    /**
     * 统一下单
     * @param userEnv 用户环境
     * @param rentInputDto
     * @return
     * @throws WxPayException
     * @throws PayPalRESTException
     */
    PrePayDto unifiedOrderCurrent(UserEnv userEnv, RentInputDto rentInputDto) throws WxPayException, PayPalRESTException;

    /**
     * 支付成功
     * @param paymentDto
     */
    void rentPaySuccess(PaymentDto paymentDto);

    /**
     * 更新租赁信息
     * @param lease
     */
    void saveLease(Lease lease);

    /**
     * 归还设备
     * @param deviceId 设备id
     * @param powerBankId 充电宝id
     * @return true 成功 false 失败
     */
    boolean returnDevice(String deviceId, String powerBankId);

    /**
     * 租赁报告
     * @param rentReportInput
     */
    void rentReport(RentReportInput rentReportInput);

    /**
     * 费用计算
     * @param productType
     * @param beginTime
     * @param endTime
     * @return
     */
    BigDecimal calculatedAmount(boolean en, int productType, Date beginTime, Date endTime);

    /**
     * 弹出设备
     * @param leaseId 租赁id
     */
    boolean popDevice(int userId, int leaseId);

    /**
     * 强制弹出设备
     * @param deviceId 设备id
     * @param powerBankId 充电宝id
     * @return
     */
    boolean popUpReport(String deviceId, String powerBankId);

    /**
     * 查询还未结束的租借
     * @return
     */
    List<Lease> findNotOver();
}
