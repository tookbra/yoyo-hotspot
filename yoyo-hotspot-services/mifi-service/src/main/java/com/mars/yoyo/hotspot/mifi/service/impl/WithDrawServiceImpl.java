package com.mars.yoyo.hotspot.mifi.service.impl;

import com.mars.yoyo.hotspot.exception.BizFeignException;
import com.mars.yoyo.hotspot.mifi.constant.CommonConstant;
import com.mars.yoyo.hotspot.mifi.dao.UserAccountWithdrawMapper;
import com.mars.yoyo.hotspot.mifi.domain.PayOrder;
import com.mars.yoyo.hotspot.mifi.domain.User;
import com.mars.yoyo.hotspot.mifi.domain.UserAccount;
import com.mars.yoyo.hotspot.mifi.domain.UserAccountWithdraw;
import com.mars.yoyo.hotspot.mifi.enums.PayChannelEnum;
import com.mars.yoyo.hotspot.mifi.service.OrderService;
import com.mars.yoyo.hotspot.mifi.service.UserAccountService;
import com.mars.yoyo.hotspot.mifi.service.UserService;
import com.mars.yoyo.hotspot.mifi.service.WithDrawService;
import com.mars.yoyo.hotspot.mifi.vo.WithDrawVo;
import com.mars.yoyo.hotspot.util.NumberUtil;
import lombok.extern.slf4j.Slf4j;
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
 * @date 2018/6/21
 * @description
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class, readOnly = true)
public class WithDrawServiceImpl implements WithDrawService {

    @Resource
    UserAccountWithdrawMapper userAccountWithdrawMapper;

    @Autowired
    OrderService orderService;

    @Autowired
    UserAccountService userAccountService;

    @Autowired
    UserService userService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public WithDrawVo apply(int userId) {
        WithDrawVo withDrawVo = new WithDrawVo();
        PayOrder payOrder = orderService.findTop1ByOrderType(userId, CommonConstant.ORDER_TYPE_DEPOSIT);
        if(payOrder == null) {
            throw new BizFeignException("biz.deposit.not.pay", CommonConstant.CODE_DEPOSIT_NOT_PAY);
        }

        Example example = new Example(UserAccountWithdraw.class);
        example.createCriteria().andEqualTo("userId", userId).andEqualTo("orderNo", payOrder.getOrderId());
        UserAccountWithdraw userAccountWithdraw = userAccountWithdrawMapper.selectOneByExample(example);
        BigDecimal money = payOrder.getAmount();
        if(payOrder.getPayChannel() == PayChannelEnum.PAYPAL.getCode()) {
            withDrawVo.setLang(CommonConstant.LANG_EN);
        } else {
            withDrawVo.setLang(CommonConstant.LANG_CH);
        }
        withDrawVo.setMoney(money);
        if(userAccountWithdraw != null) {
            withDrawVo.setStatus(userAccountWithdraw.getStatus());
            return withDrawVo;
        }
        userAccountWithdraw = new UserAccountWithdraw();
        userAccountWithdraw.setOrderNo(payOrder.getOrderId());
        userAccountWithdraw.setUserId(userId);
        userAccountWithdraw.setMoney(payOrder.getAmount());
        userAccountWithdrawMapper.insertSelective(userAccountWithdraw);

        Date date = DateTime.now().toDate();
        UserAccount userAccount = userAccountService.getUser(userId);
        if(userAccount != null) {
            userAccount.setDeposit(BigDecimal.ZERO);
            userAccount.setDepositEn(BigDecimal.ZERO);
            userAccount.setModifyTime(date);
            userAccountService.saveAccount(userAccount);
        }

        User user = userService.getByUserId(userId);
        if(user != null) {
            user.setDeposited(false);
            user.setModifyTime(date);
            userService.saveUser(user);
        }

        if(payOrder != null) {
            withDrawVo.setStatus(1);
            return withDrawVo;
        }

        withDrawVo.setStatus(0);
        return withDrawVo;
    }
}
