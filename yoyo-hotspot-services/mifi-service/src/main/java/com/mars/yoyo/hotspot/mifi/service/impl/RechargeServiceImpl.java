package com.mars.yoyo.hotspot.mifi.service.impl;

import com.github.binarywang.wxpay.exception.WxPayException;
import com.mars.yoyo.hotspot.common.dps.secutiry.UserEnv;
import com.mars.yoyo.hotspot.mifi.constant.CommonConstant;
import com.mars.yoyo.hotspot.mifi.dao.UserAccountRechargeMapper;
import com.mars.yoyo.hotspot.mifi.domain.PayOrder;
import com.mars.yoyo.hotspot.mifi.domain.UserAccount;
import com.mars.yoyo.hotspot.mifi.domain.UserAccountRecharge;
import com.mars.yoyo.hotspot.mifi.dto.PrePayDto;
import com.mars.yoyo.hotspot.mifi.enums.ClientTypeEnum;
import com.mars.yoyo.hotspot.mifi.enums.PayChannelEnum;
import com.mars.yoyo.hotspot.mifi.service.OrderService;
import com.mars.yoyo.hotspot.mifi.service.RechargeService;
import com.mars.yoyo.hotspot.mifi.service.UserAccountService;
import com.mars.yoyo.hotspot.util.NumberUtil;
import com.mars.yoyo.hotspot.util.SeqUtil;
import com.paypal.base.rest.PayPalRESTException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author tookbra
 * @date 2018/6/5
 * @description
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class, readOnly = true)
public class RechargeServiceImpl implements RechargeService {

    @Resource
    UserAccountRechargeMapper userAccountRechargeMapper;

    @Autowired
    OrderService orderService;

    @Autowired
    UserAccountService userAccountService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public PrePayDto recharge(UserEnv userEnv, String path, String orderNo, PayChannelEnum payChannelEnum,
                              ClientTypeEnum clientTypeEnum, BigDecimal amount) throws PayPalRESTException, WxPayException {

        if(StringUtils.isNotBlank(orderNo)) {
            boolean flag = orderService.findByOrderNoQuery(orderNo);
            if(flag) {
                PrePayDto prePayDto = new PrePayDto();
                prePayDto.setPayResult(true);
                return prePayDto;
            }
        }

        orderNo = SeqUtil.generatorId(userEnv.getUserId());

        PayOrder payOrder = new PayOrder();
        if(userEnv.getLang().equals(CommonConstant.LANG_CH)) {
            payOrder.setBody("充值");
        } else {
            payOrder.setBody("recharge");
        }
        UserAccountRecharge userAccountRecharge = new UserAccountRecharge();
        userAccountRecharge.setUserId(userEnv.getUserId());
        userAccountRecharge.setAmount(amount);
        userAccountRecharge.setStatus(0);
        userAccountRecharge.setPayChannel(payChannelEnum.getCode());
        userAccountRecharge.setOrderNo(orderNo);
        userAccountRechargeMapper.insertSelective(userAccountRecharge);


        payOrder.setOrderId(orderNo);
        payOrder.setUserId(userEnv.getUserId());
        payOrder.setPayIp(userEnv.getRequestIp());
        payOrder.setOrderType(CommonConstant.ORDER_TYPE_RECHARGE);
        payOrder.setPayChannel(payChannelEnum.getCode());
        if (userEnv.getLang().equals("en") && payChannelEnum == PayChannelEnum.WECHAT_PAY) {
            payOrder.setAmount(amount.multiply(new BigDecimal(7)));
        } else {
            payOrder.setAmount(amount);
        }
        PrePayDto prePayDto = orderService.payAdpater(clientTypeEnum, path, payChannelEnum, payOrder);
        return prePayDto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void preRecharge(String orderNo) {
        UserAccountRecharge userAccountRecharge = this.findByOrderNo(orderNo);
        if(userAccountRecharge != null) {
            userAccountRecharge.setStatus(CommonConstant.ORDER_STATE_PROCESSING);
            userAccountRecharge.setModityTime(DateTime.now().toDate());
            this.save(userAccountRecharge);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void rechargeConfirm(String orderNo, UserAccount userAccount) {
        UserAccountRecharge userAccountRecharge = this.findByOrderNo(orderNo);
        if(userAccountRecharge != null) {
            Date now = DateTime.now().toDate();
            userAccountRecharge.setStatus(CommonConstant.ORDER_STATE_SUCCESS);
            userAccountRecharge.setModityTime(now);
            this.save(userAccountRecharge);

            userAccount.setModifyTime(now);
            userAccountService.saveAccount(userAccount);
        }
    }

    private UserAccountRecharge findByOrderNo(String orderNo) {
        Example example = new Example(UserAccountRecharge.class);
        example.createCriteria().andEqualTo("orderNo", orderNo);
        UserAccountRecharge userAccountRecharge = userAccountRechargeMapper.selectOneByExample(example);
        return userAccountRecharge;
    }

    @Transactional(rollbackFor = Exception.class)
    public void save(UserAccountRecharge userAccountRecharge) {
        userAccountRechargeMapper.updateByPrimaryKeySelective(userAccountRecharge);
    }
}
