package com.mars.yoyo.hotspot.admin.service;

import com.mars.yoyo.hotspot.admin.params.UserParameter;
import com.mars.yoyo.hotspot.admin.params.UserUpdateParameter;
import com.mars.yoyo.hotspot.admin.resutls.UserView;
import com.mars.yoyo.hotspot.admin.resutls.base.ListResultEx;
import com.mars.yoyo.hotspot.admin.resutls.base.ResultEx;
import com.mars.yoyo.hotspot.result.RestResult;

/**
 * 用户管理
 *
 * @author admin
 * @create 2018/5/14
 * @since 1.0.0
 */
public interface UserService {

    /**
     * 分页查询用户列表
     * @param parameter
     * @return
     */
    ListResultEx<UserView> getUserList(UserParameter parameter);

    /**
     * 获取用户详情
     * @param userId
     * @return
     */
    ResultEx getUserInfoById(Integer userId);

    /**
     * 更新用户信息
     * @param parameter
     * @return
     */
    RestResult updateUserInfo(UserUpdateParameter parameter);

}
