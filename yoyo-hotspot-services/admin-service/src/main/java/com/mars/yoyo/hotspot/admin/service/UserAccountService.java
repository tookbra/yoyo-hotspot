package com.mars.yoyo.hotspot.admin.service;

import com.mars.yoyo.hotspot.admin.resutls.UserAccountView;
import com.mars.yoyo.hotspot.result.RestResult;

/**
 * 用户账户接口
 *
 * @author admin
 * @create 2018/5/14
 * @since 1.0.0
 */
public interface UserAccountService {

    /**
     * 查询用户的账户信息
     * @param userId
     * @return
     */
    RestResult<UserAccountView> getUserAccount(Integer userId);

}
