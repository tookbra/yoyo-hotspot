package com.mars.yoyo.hotspot.mifi.service.impl;

import com.mars.yoyo.hotspot.mifi.constant.CommonConstant;
import com.mars.yoyo.hotspot.mifi.dao.RedEnvelopeItemMapper;
import com.mars.yoyo.hotspot.mifi.dao.UserRedEnvelopeMapper;
import com.mars.yoyo.hotspot.mifi.domain.RedEnvelopeItem;
import com.mars.yoyo.hotspot.mifi.domain.UserAccount;
import com.mars.yoyo.hotspot.mifi.domain.UserRedEnvelope;
import com.mars.yoyo.hotspot.mifi.service.RedEnvelopeService;
import com.mars.yoyo.hotspot.mifi.service.UserAccountService;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author tookbra
 * @date 2018/7/8
 * @description
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class, readOnly = true)
public class RedEnvelopeServiceImpl implements RedEnvelopeService {

    @Resource
    RedEnvelopeItemMapper redEnvelopeItemMapper;

    @Resource
    UserRedEnvelopeMapper userRedEnvelopeMapper;

    @Autowired
    UserAccountService userAccountService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BigDecimal receiveRed(int userId, String lang) {
        RedEnvelopeItem redEnvelopeItem = redEnvelopeItemMapper.findByRandom();
        Date now = DateTime.now().toDate();
        if(redEnvelopeItem == null) {
            return BigDecimal.ZERO;
        }

        redEnvelopeItem.setIsReceive(true);
        redEnvelopeItem.setModifyTime(now);
        this.saveRedEnvelopeItem(redEnvelopeItem);

        UserAccount userAccount = userAccountService.getUser(userId);
        if(lang.equals(CommonConstant.LANG_CH)) {
            userAccount.setBalance(userAccount.getBalance().add(redEnvelopeItem.getAmount()));
        } else {
            userAccount.setBalance(userAccount.getBalanceEn().add(redEnvelopeItem.getAmount()));
        }
        userAccount.setModifyTime(now);
        userAccount.setRedEnvelopeNum(userAccount.getRedEnvelopeNum() + 1);
        userAccountService.saveAccount(userAccount);

        UserRedEnvelope userRedEnvelope = new UserRedEnvelope();
        userRedEnvelope.setUserId(userId);
        userRedEnvelope.setAmount(redEnvelopeItem.getAmount());
        userRedEnvelope.setRedEnvelopeId(redEnvelopeItem.getRedEnvelopeId());
        userRedEnvelope.setRedItemId(redEnvelopeItem.getId());
        this.addUserRed(userRedEnvelope);
        return redEnvelopeItem.getAmount();
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveRedEnvelopeItem(RedEnvelopeItem redEnvelopeItem) {
        redEnvelopeItemMapper.updateByPrimaryKeySelective(redEnvelopeItem);
    }

    @Transactional(rollbackFor = Exception.class)
    public void addUserRed(UserRedEnvelope userRedEnvelope) {
        userRedEnvelopeMapper.insertSelective(userRedEnvelope);
    }
}
