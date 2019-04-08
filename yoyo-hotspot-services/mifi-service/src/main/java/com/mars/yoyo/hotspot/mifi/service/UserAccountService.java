package com.mars.yoyo.hotspot.mifi.service;

import com.mars.yoyo.hotspot.mifi.domain.UserAccount;

/**
 * @author tookbra
 * @date 2018/5/21
 * @description
 */
public interface UserAccountService {

    /**
     * 获取用户
     * @param userId
     * @return
     */
    UserAccount getUser(int userId);

    /**
     * 添加用户账户
     * @param userAccount
     */
    void addAccount(UserAccount userAccount);

    /**
     * 更新用户账户
     * @param userAccount
     */
    void saveAccount(UserAccount userAccount);
}
