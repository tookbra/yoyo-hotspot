package com.mars.yoyo.hotspot.admin.service;

import com.mars.yoyo.hotspot.admin.params.LoginParameter;
import com.mars.yoyo.hotspot.admin.params.UpdatePasswordParameter;
import com.mars.yoyo.hotspot.admin.resutls.AdminView;
import com.mars.yoyo.hotspot.result.RestResult;

/**
 * 登录接口
 *
 * @author admin
 * @create 2018/5/16
 */
public interface LoginService {

    /**
     * 登录接口
     * @param parameter
     * @return
     */
    RestResult<AdminView> login(LoginParameter parameter);

    /**
     * 密码修改
     * @param parameter
     * @return
     */
    RestResult restPwd(UpdatePasswordParameter parameter);

    /**
     * 忘记密码
     * @param parameter
     * @return
     */
    RestResult fogetPwd(UpdatePasswordParameter parameter);

}
