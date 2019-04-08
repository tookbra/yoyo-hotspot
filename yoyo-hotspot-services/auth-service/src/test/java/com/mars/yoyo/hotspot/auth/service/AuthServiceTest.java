package com.mars.yoyo.hotspot.auth.service;

import com.mars.yoyo.hotspot.auth.BaseTest;
import com.mars.yoyo.hotspot.auth.dto.input.LoginInputDto;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author tookbra
 * @date 2018/5/27
 * @description
 */
public class AuthServiceTest extends BaseTest {

    @Autowired
    AuthService authService;

    @Test
    public void loginTest() throws Exception {
        LoginInputDto loginInputDto = new LoginInputDto();
        loginInputDto.setUsername("15906695726");
        loginInputDto.setCaptcha("123456");
        authService.login(loginInputDto);
    }
}
