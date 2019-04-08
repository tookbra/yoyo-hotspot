package com.mars.yoyo.hotspot.mifi.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult;
import com.github.binarywang.wxpay.bean.order.WxPayMwebOrderResult;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.bean.result.WxPayOrderQueryResult;
import com.github.binarywang.wxpay.bean.result.WxPayUnifiedOrderResult;
import com.github.binarywang.wxpay.constant.WxPayConstants;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.pagehelper.PageHelper;
import com.mars.yoyo.hotspot.common.dps.secutiry.UserEnv;
import com.mars.yoyo.hotspot.exception.BizFeignException;
import com.mars.yoyo.hotspot.mifi.cache.ConfigDictionaryCache;
import com.mars.yoyo.hotspot.mifi.config.PaypalProperties;
import com.mars.yoyo.hotspot.mifi.config.WechatProperties;
import com.mars.yoyo.hotspot.mifi.constant.CommonConstant;
import com.mars.yoyo.hotspot.mifi.constant.DictionaryConstant;
import com.mars.yoyo.hotspot.mifi.dao.PayOrderMapper;
import com.mars.yoyo.hotspot.mifi.domain.*;
import com.mars.yoyo.hotspot.mifi.dto.PaymentDto;
import com.mars.yoyo.hotspot.mifi.dto.PrePayDto;
import com.mars.yoyo.hotspot.mifi.enums.ClientTypeEnum;
import com.mars.yoyo.hotspot.mifi.enums.PayChannelEnum;
import com.mars.yoyo.hotspot.mifi.enums.PaypalPaymentIntent;
import com.mars.yoyo.hotspot.mifi.enums.PaypalPaymentMethod;
import com.mars.yoyo.hotspot.mifi.pojo.SceneInfo;
import com.mars.yoyo.hotspot.mifi.service.*;
import com.mars.yoyo.hotspot.mifi.vo.OrderConfirmVo;
import com.mars.yoyo.hotspot.util.JacksonUtil;
import com.mars.yoyo.hotspot.util.NumberUtil;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.util.RandomUtils;
import org.apache.ibatis.session.RowBounds;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author tookbra
 * @date 2018/5/21
 * @description
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class, readOnly = true)
public class OrderServiceImpl implements OrderService {

    @Resource
    PayOrderMapper payOrderMapper;

    @Autowired
    WechatProperties wechatProperties;

    @Autowired
    WxPayService wxPayService;

    @Autowired
    PaypalProperties paypalProperties;

    @Autowired
    APIContext apiContext;

    @Autowired
    UserService userService;

    @Autowired
    UserAccountService userAccountService;

    @Autowired
    LeaseService leaseService;

    @Autowired
    RechargeService rechargeService;

    @Autowired
    UserAuthService userAuthService;

    @Autowired
    ConfigDictionaryCache configDictionaryCache;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayOrder addOrder(PayOrder order) {
        payOrderMapper.insertSelective(order);
        return order;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayOrder save(PayOrder order) {
        payOrderMapper.updateByPrimaryKeySelective(order);
        return order;
    }

    @Override
    public PayOrder findByOrderNo(int userId, String orderNo) {
        Example example = new Example(PayOrder.class);
        example.createCriteria().andEqualTo("userId", userId)
                .andEqualTo("orderId", orderNo);
        PayOrder payOrder = payOrderMapper.selectOneByExample(example);
        return payOrder;
    }

    public PayOrder findByOrderNo(String orderNo) {
        Example example = new Example(PayOrder.class);
        example.createCriteria().andEqualTo("orderId", orderNo);
        PayOrder payOrder = payOrderMapper.selectOneByExample(example);
        return payOrder;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PrePayDto payAdpater(ClientTypeEnum clientTypeEnum, String path, PayChannelEnum payChannelEnum,
                                PayOrder payOrder) throws WxPayException, PayPalRESTException {
        PrePayDto prePayDto = new PrePayDto();
        switch (payChannelEnum) {
            case WECHAT_PAY:
                prePayDto = wxPay(clientTypeEnum, path, payOrder);
                prePayDto.setPayChannel(PayChannelEnum.WECHAT_PAY.getChannel());
                break;
            case PAYPAL:
                prePayDto = paypal(payOrder, path);
                prePayDto.setPayChannel(PayChannelEnum.PAYPAL.getChannel());
                break;
            default:
                throw new BizFeignException("biz.pay.channel.not.found");
        }
        prePayDto.setOrderNo(payOrder.getPaymentId());
        return prePayDto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void orderConfirm(String paymentId, String payerId) throws PayPalRESTException {
        Payment payment = new Payment();
        payment.setId(paymentId);
        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(payerId);
        payment = payment.execute(apiContext, paymentExecution);
        PayOrder payOrder = this.findByPaymentId(paymentId);
        if(payOrder == null) {
            throw new BizFeignException("biz.pay.not.found");
        }

        if(payOrder.getState() == CommonConstant.ORDER_STATE_PROCESSING) {
            return;
        }

        if(payment.getState().equals("approved")) {
            payOrder.setState(CommonConstant.ORDER_STATE_PROCESSING);
        } else {
            payOrder.setState(CommonConstant.ORDER_STATE_FAILED);
        }

        // 充值
        if(payOrder.getOrderType() == CommonConstant.ORDER_TYPE_RECHARGE) {
            rechargeService.preRecharge(payOrder.getOrderId());
        }
        // 租借
        if(payOrder.getOrderType() == CommonConstant.ORDER_TYPE_RENT
                || payOrder.getOrderType() == CommonConstant.ORDER_TYPE_RENEWS
                || payOrder.getOrderType() == CommonConstant.ORDER_TYPE_RETURN) {
//            notify(payOrder);
        }
        this.save(payOrder);
    }

    @Override
    public OrderConfirmVo orderConfirm(String paymentId) throws WxPayException {
        OrderConfirmVo orderConfirmVo = new OrderConfirmVo();
        orderConfirmVo.setFlag(findByOrderNoQuery(paymentId));
        if(orderConfirmVo.isFlag()) {
            PayOrder payOrder = this.findByPaymentId(paymentId);
            Lease lease = leaseService.findByOrderId(payOrder.getOrderId());
            orderConfirmVo.setRentId(lease.getId());
        }

        return orderConfirmVo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void notifyPaypal(String paymentId) {
        PayOrder payOrder = this.findByPaymentId(paymentId);
        if(payOrder == null) {
            return;
        }
        if(payOrder.getState() == CommonConstant.ORDER_STATE_SUCCESS) {
            return;
        }
        notify(payOrder);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void notifyWechat(String orderNo) {
        PayOrder payOrder = this.findByOrderNo(orderNo);
        if(payOrder == null) {
            return;
        }
        if(payOrder.getState() == CommonConstant.ORDER_STATE_SUCCESS) {
            return;
        }
        notify(payOrder);
    }

    @Transactional(rollbackFor = Exception.class)
    public void notify(PayOrder payOrder) {
        Date now = DateTime.now().toDate();
        // 押金确认
        if(payOrder.getOrderType() == CommonConstant.ORDER_TYPE_DEPOSIT) {
            int userId = payOrder.getUserId();
            User user = userService.getByUserId(userId);
            user.setDeposited(true);
            user.setModifyTime(now);
            userService.saveUser(user);

            String depositMoneyEn = configDictionaryCache.getDictionaryName(DictionaryConstant.DEPOSIT_MONEY_EN);
            String depositMoney = configDictionaryCache.getDictionaryName(DictionaryConstant.DEPOSIT_MONEY_CN);
            UserAccount userAccount = userAccountService.getUser(userId);
            if(payOrder.getCurrency().equals("usd") || user.getEn()) {
                userAccount.setDepositEn(new BigDecimal(depositMoneyEn));
            } else {
                userAccount.setDeposit(new BigDecimal(depositMoney));
            }
            userAccount.setModifyTime(now);
            userAccountService.saveAccount(userAccount);

            payOrder.setState(CommonConstant.ORDER_STATE_SUCCESS);
            payOrder.setNotifyCount(payOrder.getNotifyCount() + 1);
            payOrder.setPaySuccessTime(now);
            this.save(payOrder);
        }

        // 租借确认
        if(payOrder.getOrderType() == CommonConstant.ORDER_TYPE_RENT
                || payOrder.getOrderType() == CommonConstant.ORDER_TYPE_RENEWS
                || payOrder.getOrderType() == CommonConstant.ORDER_TYPE_RETURN) {
            payOrder.setState(CommonConstant.ORDER_STATE_SUCCESS);
            payOrder.setNotifyCount(payOrder.getNotifyCount() + 1);
            payOrder.setPaySuccessTime(DateTime.now().toDate());
            this.save(payOrder);
            String ext = payOrder.getExt();
            PaymentDto paymentDto = JSON.parseObject(ext, PaymentDto.class);
            paymentDto.setUserId(payOrder.getUserId());
            paymentDto.setPayChannel(payOrder.getPayChannel());
            leaseService.rentPaySuccess(paymentDto);

        }

        // 充值确认
        if(payOrder.getOrderType() == CommonConstant.ORDER_TYPE_RECHARGE) {
            payOrder.setState(CommonConstant.ORDER_STATE_SUCCESS);
            payOrder.setNotifyCount(payOrder.getNotifyCount() + 1);
            payOrder.setModifyTime(now);
            payOrder.setPaySuccessTime(now);
            this.save(payOrder);

            UserAccount userAccount = userAccountService.getUser(payOrder.getUserId());
            if(userAccount != null) {
                if (payOrder.getPayChannel() == PayChannelEnum.WECHAT_PAY.getCode()) {
                    userAccount.setBalance(userAccount.getBalance().add(payOrder.getAmount()));
                } else {
                    userAccount.setBalanceEn(userAccount.getBalanceEn().add(payOrder.getAmount()));
                }
                rechargeService.rechargeConfirm(payOrder.getOrderId(), userAccount);
            }

        }
    }

    @Override
    public PayOrder findByOrderType(int userId, int orderType) {
        Example example = new Example(PayOrder.class);
        example.createCriteria().andEqualTo("userId", userId)
                .andEqualTo("orderType", orderType).andEqualTo("state", CommonConstant.ORDER_STATE_SUCCESS);
        PayOrder payOrder = payOrderMapper.selectOneByExample(example);
        return payOrder;
    }

    @Override
    public PayOrder findTop1ByOrderType(int userId, int orderType) {
        Example example = new Example(PayOrder.class);
        PageHelper.startPage(1,1);
        example.orderBy("createTime").desc();
        example.createCriteria().andEqualTo("userId", userId)
                .andEqualTo("orderType", orderType).andEqualTo("state", CommonConstant.ORDER_STATE_SUCCESS);
        PayOrder payOrder = payOrderMapper.selectOneByExample(example);
        return payOrder;
    }

    @Override
    public boolean findByOrderNoQuery(String orderNo) throws WxPayException {
        PayOrder payOrder = this.findByPaymentId(orderNo);
        if(payOrder == null) {
            return false;
        }
        if(payOrder.getState() == CommonConstant.ORDER_STATE_SUCCESS) {
            return true;
        }
        if(payOrder.getPayChannel() == PayChannelEnum.WECHAT_PAY.getCode()) {
            WxPayOrderQueryResult wxPayOrderQueryResult = wxPayService.queryOrder("", payOrder.getOrderId());
            if (wxPayOrderQueryResult.getResultCode().equals(WxPayConstants.ResultCode.SUCCESS)
                    && wxPayOrderQueryResult.getTradeState().equals(WxPayConstants.WxpayTradeStatus.SUCCESS)) {
                return wxPayOrderQueryResult.getResultCode().equals(WxPayConstants.ResultCode.SUCCESS);
            } else {
                return false;
            }
        } else if(payOrder.getPayChannel() == PayChannelEnum.BALANCE.getCode()) {
          return true;
        } else {
            try {
                Payment payment = Payment.get(apiContext, orderNo);
                if(payment.getState().equals("approved")) {
                    return true;
                } else {
                    return false;
                }
            } catch (Exception e) {
                return false;
            }
        }
    }

    private PrePayDto wxPay(ClientTypeEnum clientTypeEnum, String path, PayOrder payOrder) throws WxPayException {
        PrePayDto prePayDto = new PrePayDto();
        WxPayUnifiedOrderRequest wxPayUnifiedOrderRequest = new WxPayUnifiedOrderRequest();
        wxPayUnifiedOrderRequest.setBody(payOrder.getBody());

        if(wechatProperties.getPay().isUseSandboxEnv()) {
            String sandboxKey = wxPayService.getSandboxSignKey();
            wxPayService.getConfig().setMchKey(sandboxKey);
        }
        wxPayUnifiedOrderRequest.setOutTradeNo(payOrder.getOrderId());
//        wxPayUnifiedOrderRequest.setTotalFee(payOrder.getAmount().intValue());
        // todo test
        wxPayUnifiedOrderRequest.setTotalFee(1);
        WechatProperties.Pay wxPay = wechatProperties.getPay();
        //APP和网页支付提交用户端ip
        wxPayUnifiedOrderRequest.setSpbillCreateIp(payOrder.getPayIp());
        String clientType = getTradeType(clientTypeEnum);
        wxPayUnifiedOrderRequest.setTradeType(clientType);
        wxPayUnifiedOrderRequest.setNotifyUrl(wxPay.getNotifyUrl());
        payOrder.setNotifyUrl(wxPay.getNotifyUrl());
        if(clientType.equals(WxPayConstants.TradeType.JSAPI)) {
            UserAuth userAuth = userAuthService.findByUserId(payOrder.getUserId(), "wechat");
            if(userAuth == null) {
                throw new BizFeignException("biz.pay.openId.not.found", CommonConstant.PAY_WECHAT_NOT_FOUND);
            }
            wxPayUnifiedOrderRequest.setOpenid(userAuth.getOpenId());
        }
        //附加数据 门店
        wxPayUnifiedOrderRequest.setAttach("test");
        SceneInfo sceneInfo = new SceneInfo();
        wxPayUnifiedOrderRequest.setSceneInfo(JacksonUtil.stringify(sceneInfo));
        WxPayUnifiedOrderResult wxPayUnifiedOrderResult = wxPayService.unifiedOrder(wxPayUnifiedOrderRequest);

        if(wxPayUnifiedOrderResult.getReturnCode().equals(WxPayConstants.ResultCode.SUCCESS)
                && wxPayUnifiedOrderResult.getResultCode().equals(WxPayConstants.ResultCode.SUCCESS)) {
            payOrder.setState(CommonConstant.ORDER_STATE_PROCESSING);
            payOrder.setPaymentId(wxPayUnifiedOrderResult.getPrepayId());
            prePayDto.setOrderNo(wxPayUnifiedOrderResult.getPrepayId());
            prePayDto.setRedirectUrl(wxPayUnifiedOrderResult.getMwebUrl());
        } else {
            payOrder.setErrorCode(wxPayUnifiedOrderResult.getErrCode());
            payOrder.setErrorMsg(wxPayUnifiedOrderResult.getReturnMsg());
            payOrder.setState(CommonConstant.ORDER_STATE_FAILED);
            log.error("pay error={}", wxPayUnifiedOrderResult.getReturnMsg());
        }
        switch (clientTypeEnum) {
            case H5:
                WxPayMwebOrderResult wxPayMwebOrderResult = wxPayService.createOrder(wxPayUnifiedOrderRequest);
                prePayDto.setRedirectUrl(wxPayMwebOrderResult.getMwebUrl());
                break;
            case MP:
                WxPayMpOrderResult wxPayMpOrderResult = wxPayService.createOrder(wxPayUnifiedOrderRequest);
                prePayDto.setWxPayMpOrderResult(wxPayMpOrderResult);
                break;
            case NATIVE:
                WxPayMpOrderResult wxPayNativeOrderResult = wxPayService.createOrder(wxPayUnifiedOrderRequest);
                prePayDto.setWxPayMpOrderResult(wxPayNativeOrderResult);
                break;
            default:
                throw new BizFeignException("biz.pay.channel.not.found");
        }
        this.addOrder(payOrder);
        return prePayDto;
    }

    private PrePayDto paypal(PayOrder payOrder, String path) throws PayPalRESTException {
        PrePayDto prePayDto = new PrePayDto();
        payOrder.setCurrency("usd");

        String returnUrl = "";
        Amount amount = new Amount();
        amount.setCurrency("USD");
        amount.setTotal(payOrder.getAmount().toString());

        Transaction transaction = new Transaction();
        transaction.setDescription(payOrder.getBody());
        transaction.setAmount(amount);

        List<Transaction> transactions = new ArrayList<Transaction>();
        transactions.add(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod(PaypalPaymentMethod.paypal.toString());

        RedirectUrls redirectUrls = new RedirectUrls();
        payOrder.setNotifyUrl(paypalProperties.getNotifyUrl());
        redirectUrls.setCancelUrl(paypalProperties.getCancelUrl() + path);
        redirectUrls.setReturnUrl(paypalProperties.getReturnUrl() + path);

        Payment payment = new Payment();
        payment.setIntent(PaypalPaymentIntent.sale.toString());
        payment.setPayer(payer);
        payment.setTransactions(transactions);
        payment.setRedirectUrls(redirectUrls);

        // 创建张贴到API服务
        // 使用一个有效的访问令牌
        // 返回的对象包含状态的支付;
        Payment createdPayment = payment.create(apiContext);
        // ###Payment Approval Url
        Iterator<Links> links = createdPayment.getLinks().iterator();
        while (links.hasNext()) {
            Links link = links.next();
            if (link.getRel().equalsIgnoreCase("approval_url")) {
                returnUrl = link.getHref();
            }
        }
        payOrder.setPaymentId(createdPayment.getId());
        prePayDto.setOrderNo(createdPayment.getId());
        prePayDto.setRedirectUrl(returnUrl);

        this.addOrder(payOrder);
        return prePayDto;
    }

    private String getTradeType(ClientTypeEnum clientTypeEnum) {
        switch (clientTypeEnum) {
            case H5:
                return WxPayConstants.TradeType.MWEB;
            case MP:
                return WxPayConstants.TradeType.JSAPI;
            case NATIVE:
                return WxPayConstants.TradeType.NATIVE;
            default:
                throw new BizFeignException("biz.pay.channel.not.found");
        }
    }

    private PayOrder findByExt(String paymentId) {
        Example example = new Example(PayOrder.class);
        example.createCriteria().andEqualTo("ext", paymentId);
        PayOrder payOrder = payOrderMapper.selectOneByExample(example);
        return payOrder;
    }

    private PayOrder findByPaymentId(String paymentId) {
        Example example = new Example(PayOrder.class);
        example.createCriteria().andEqualTo("paymentId", paymentId);
        PayOrder payOrder = payOrderMapper.selectOneByExample(example);
        return payOrder;
    }
}
