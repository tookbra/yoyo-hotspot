package com.mars.yoyo.hotspot.mifi.service.impl;

import com.mars.yoyo.hotspot.mifi.dao.UserAccountMapper;
import com.mars.yoyo.hotspot.mifi.domain.UserAccount;
import com.mars.yoyo.hotspot.mifi.service.UserAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author tookbra
 * @date 2018/5/21
 * @description
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class, readOnly = true)
public class UserAccountServiceImpl implements UserAccountService {

    @Autowired
    UserAccountMapper userAccountMapper;

    @Override
    public UserAccount getUser(int userId) {
        UserAccount userAccount = userAccountMapper.selectByPrimaryKey(userId);
        return userAccount;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addAccount(UserAccount userAccount) {
        int count = userAccountMapper.insertSelective(userAccount);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveAccount(UserAccount userAccount) {
        userAccountMapper.updateByPrimaryKeySelective(userAccount);
    }
}
