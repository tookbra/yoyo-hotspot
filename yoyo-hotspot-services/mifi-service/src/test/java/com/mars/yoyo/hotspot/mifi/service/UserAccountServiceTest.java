package com.mars.yoyo.hotspot.mifi.service;

import com.mars.yoyo.hotspot.mifi.BaseTest;
import com.mars.yoyo.hotspot.mifi.domain.UserAccount;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

/**
 * @author tookbra
 * @date 2018/5/21
 * @description
 */
public class UserAccountServiceTest extends BaseTest {

    @Autowired
    UserAccountService userAccountService;

    @Test
    public void addUserAccount() {
        UserAccount userAccount = new UserAccount();
        userAccount.setUserId(1);
//        userAccount.setBalance(BigDecimal.ZERO);
//        userAccount.setCouponNum(0);
//        userAccount.setDeposit(BigDecimal.ZERO);
//        userAccount.setIntegral(0);
//        userAccount.setRedEnvelopeNum(0);
        userAccountService.addAccount(userAccount);
    }
}
