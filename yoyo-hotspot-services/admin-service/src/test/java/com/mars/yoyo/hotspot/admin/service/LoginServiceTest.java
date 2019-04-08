package com.mars.yoyo.hotspot.admin.service;

import com.mars.yoyo.hotspot.admin.BaseTest;
import com.mars.yoyo.hotspot.admin.params.LoginParameter;
import com.mars.yoyo.hotspot.result.RestResult;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 登录测试
 *
 * @author admin
 * @create 2018/5/22
 */
public class LoginServiceTest extends BaseTest {

    @Autowired
    LoginService loginService;

    @Test
    public void testLogin() {
        LoginParameter parameter = new LoginParameter();
        parameter.setName("admin");
        parameter.setPassword("123456");

        RestResult result = loginService.login(parameter);
        Assert.assertNotNull(result.getData());
    }


}
